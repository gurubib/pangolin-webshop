package hu.bme.crysys.homework.pangolin.webshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import javax.xml.bind.ValidationException;
import java.util.Optional;
import java.util.Set;


import hu.bme.crysys.homework.pangolin.webshop.configuration.RoleConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.dto.*;
import hu.bme.crysys.homework.pangolin.webshop.model.Role;
import hu.bme.crysys.homework.pangolin.webshop.model.User;
import hu.bme.crysys.homework.pangolin.webshop.repository.RoleRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleConfiguration roleConfiguration;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        //Optional<User> userOptional = userRepository.findByUsername(username);

        return LoginResponse.builder().status(AuthStatus.SUCCESS).build();
    }

    @Transactional
    public RegistrationResponse register(RegistrationRequest request) throws RoleNotFoundException, ValidationException {
        Role role = roleRepository.findByName(roleConfiguration.getUser()).orElseThrow(RoleNotFoundException::new);
        validateRegistrationRequest(request);

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .registrationDate(request.getRegistrationDate())
                .roles(Set.of(role))
                .build();

        userRepository.save(user);
        return RegistrationResponse.builder().status(AuthStatus.SUCCESS).build();
    }

    private void validateRegistrationRequest(RegistrationRequest request) throws ValidationException {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username is already in use!");
        }
        if (!request.getPasswordConfirm().equals(request.getPassword())) {
            throw new ValidationException("There is difference between password and confirm password!");
        }
    }

}
