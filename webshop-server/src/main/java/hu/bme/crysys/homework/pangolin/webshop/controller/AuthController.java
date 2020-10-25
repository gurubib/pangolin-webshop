package hu.bme.crysys.homework.pangolin.webshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import hu.bme.crysys.homework.pangolin.webshop.dto.*;
import hu.bme.crysys.homework.pangolin.webshop.service.AuthService;
import hu.bme.crysys.homework.pangolin.webshop.service.SecurityService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@NotNull @Valid LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);

        if (loginResponse.getStatus().equals(AuthStatus.SUCCESS)) {
            log.debug("Login: success. --> 200 - Success");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(loginResponse);
        } else {
            log.debug("Login: error. --> 403 - Access Denied");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(loginResponse);
        }
    }

    @GetMapping("/logout")
    public HttpStatus logout() {
        // TODO - write logout
        log.debug("Logout: success. --> 200 - Success");
        return HttpStatus.OK;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@NotNull @Valid RegistrationRequest request) {
        try {
            RegistrationResponse registrationResponse = authService.register(request);

            securityService.autoLogin(request.getUsername(), request.getPassword());
            log.debug("Register: success. --> 200 - Success");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(registrationResponse);
        } catch (Exception e) {
            log.debug("Register: " + e.getMessage() + " --> 403 - Error");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(RegistrationResponse.builder().status(AuthStatus.ERROR).build());
        }
    }

}
