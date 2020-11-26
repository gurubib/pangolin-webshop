package hu.bme.crysys.homework.pangolin.webshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import hu.bme.crysys.homework.pangolin.webshop.configuration.RoleConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.configuration.TokenConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.dto.*;
import hu.bme.crysys.homework.pangolin.webshop.jwt.JwtTokenProvider;
import hu.bme.crysys.homework.pangolin.webshop.model.User;
import hu.bme.crysys.homework.pangolin.webshop.repository.BlackListedJwtRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.UserRepository;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class AuthIntegrationTest {

    private static final LocalDate NOW = LocalDate.now();

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleConfiguration roleConfiguration;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private TokenConfiguration tokenConfiguration;

    @MockBean
    private BlackListedJwtRepository blackListedJwtRepository;

    @MockBean
    private WebSecurityConfiguration webSecurityConfiguration;

    @Test
    void testLogin() {
        String token = "TOKEN";
        String username = "root";
        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password("12345678")
                .build();
        when(jwtTokenProvider.createToken(anyString(), anyList())).thenReturn(token);

        ResponseEntity<LoginResponse> response = authService.login(loginRequest);

        LoginResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AuthStatus.SUCCESS, responseBody.getStatus());
        assertEquals(token, responseBody.getToken());
        assertEquals(username, responseBody.getUsername());
    }

    @Test
    void testLoginWithNonExistingUser() {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("WRONG")
                .password("WRONG")
                .build();

        ResponseEntity<LoginResponse> response = authService.login(loginRequest);

        LoginResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(AuthStatus.ERROR, responseBody.getStatus());
    }

    @Test
    void testRegister() {
        String username = "username";
        String password = "12345678";
        String email = "email@email.hu";
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username(username)
                .password(password)
                .passwordConfirm(password)
                .dateOfBirth(NOW)
                .email(email)
                .build();

        ResponseEntity<RegistrationResponse> response = authService.register(registrationRequest);

        RegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AuthStatus.SUCCESS, responseBody.getStatus());

        Optional<User> userOptional = userRepository.findByUsername(username);
        assertTrue(userOptional.isPresent());
        User registeredUser = userOptional.get();
        assertEquals(username, registeredUser.getUsername());
        assertEquals(email, registeredUser.getEmail());
        assertEquals(NOW, registeredUser.getDateOfBirth());
    }

    @Test
    void testRegisterWithAlreadyExistingUsername() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("root")
                .password("12345678")
                .passwordConfirm("12345678")
                .dateOfBirth(LocalDate.now())
                .email("email@email.hu")
                .build();

        ResponseEntity<RegistrationResponse> response = authService.register(registrationRequest);

        RegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(AuthStatus.ERROR, responseBody.getStatus());
    }

}
