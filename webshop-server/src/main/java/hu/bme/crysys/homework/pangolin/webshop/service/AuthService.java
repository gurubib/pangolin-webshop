package hu.bme.crysys.homework.pangolin.webshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import hu.bme.crysys.homework.pangolin.webshop.configuration.RoleConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.configuration.TokenConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.dto.*;
import hu.bme.crysys.homework.pangolin.webshop.jwt.JwtTokenProvider;
import hu.bme.crysys.homework.pangolin.webshop.model.BlackListedJwt;
import hu.bme.crysys.homework.pangolin.webshop.model.User;
import hu.bme.crysys.homework.pangolin.webshop.repository.BlackListedJwtRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenConfiguration tokenConfiguration;
    private final RoleConfiguration roleConfiguration;
    private final UserRepository userRepository;
    private final BlackListedJwtRepository blackListedJwtRepository;

    @Transactional
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        String username = request.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(username);

        try {
            User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            log.debug("Login: success (" + username + "). --> 200 - Success");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            LoginResponse.builder()
                                    .status(AuthStatus.SUCCESS)
                                    .username(username)
                                    .token(token)
                                    .build()
                    );
        } catch (AuthenticationException e) {
            log.debug("Login: " + e.getMessage() + " --> 403 - Forbidden");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(
                            LoginResponse.builder()
                                    .status(AuthStatus.ERROR)
                                    .build()
                    );
        }
    }

    @Transactional
    public HttpStatus logout(String bearerToken) {
        String token;

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        } else {
            log.debug("Logout: wrong token. --> 403 - Forbidden");
            return HttpStatus.FORBIDDEN;
        }

        // The token has to be expired by this time.
        LocalDate expireDate = LocalDate.now().plus(tokenConfiguration.getExpireLength(), ChronoUnit.MILLIS);

        BlackListedJwt jwtToBlackListed = new BlackListedJwt();
        jwtToBlackListed.setExpireDate(expireDate);
        jwtToBlackListed.setToken(token);

        blackListedJwtRepository.save(jwtToBlackListed);

        log.debug("Logout: success. --> 200 - Success");
        return HttpStatus.NO_CONTENT;
    }

    @Transactional
    public ResponseEntity<RegistrationResponse> register(RegistrationRequest request) {
        try {
            validateRegistrationRequest(request);
        } catch (ValidationException e) {
            log.debug("Register: " + e.getMessage() + " --> 403 - Forbidden");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(
                            RegistrationResponse.builder()
                                    .status(AuthStatus.ERROR)
                                    .build()
                    );
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .registrationDate(request.getRegistrationDate())
                .roles(List.of(roleConfiguration.getUser()))
                .build();

        userRepository.save(user);

        log.debug("Register: success. --> 200 - Success");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        RegistrationResponse.builder()
                                .status(AuthStatus.SUCCESS)
                                .build()
                );
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
