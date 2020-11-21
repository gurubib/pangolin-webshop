package hu.bme.crysys.homework.pangolin.webshop;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import hu.bme.crysys.homework.pangolin.webshop.configuration.AdminConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.configuration.RoleConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.model.User;
import hu.bme.crysys.homework.pangolin.webshop.repository.UserRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class WebshopApplication implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final AdminConfiguration adminConfiguration;
    private final RoleConfiguration roleConfiguration;
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebshopApplication.class, args);
    }

    @Override
    public void run(String... args) {
        User admin = User.builder()
                .id(-1L)
                .username(adminConfiguration.getUsername())
                .password(passwordEncoder.encode(adminConfiguration.getPassword()))
                .email(adminConfiguration.getEmail())
                .dateOfBirth(LocalDate.of(1900, 1, 1))
                .registrationDate(LocalDate.of(1900, 1, 1))
                .roles(List.of(roleConfiguration.getUser(), roleConfiguration.getAdmin()))
                .uuid(UUID.randomUUID().toString())
                .build();

        userRepository.save(admin);
    }

}
