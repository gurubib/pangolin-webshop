package hu.bme.crysys.homework.pangolin.webshop;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

import hu.bme.crysys.homework.pangolin.webshop.configuration.AdminConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.configuration.RoleConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.configuration.SecurityConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.model.Role;
import hu.bme.crysys.homework.pangolin.webshop.model.User;
import hu.bme.crysys.homework.pangolin.webshop.repository.RoleRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.UserRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class WebshopApplication implements CommandLineRunner {

    private final SecurityConfiguration securityConfiguration;
    private final AdminConfiguration adminConfiguration;
    private final RoleConfiguration roleConfiguration;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebshopApplication.class, args);
    }

    @Override
    public void run(String... args) {
        BCryptPasswordEncoder passwordEncoder = securityConfiguration.bCryptPasswordEncoder();

        Role adminRole = new Role();
        adminRole.setName(roleConfiguration.getAdmin());
		adminRole = roleRepository.save(adminRole);

        Role userRole = new Role();
		userRole.setName(roleConfiguration.getUser());
		userRole = roleRepository.save(userRole);

        User admin = User.builder()
                .id(-1L)
                .username(adminConfiguration.getUsername())
                .password(passwordEncoder.encode(adminConfiguration.getPassword()))
                .email(adminConfiguration.getEmail())
                .dateOfBirth(LocalDate.of(1900, 1, 1))
                .registrationDate(LocalDate.of(1900, 1, 1))
                .roles(Set.of(adminRole, userRole))
                .build();

        userRepository.save(admin);
    }

}
