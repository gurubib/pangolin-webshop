package hu.bme.crysys.homework.pangolin.webshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import hu.bme.crysys.homework.pangolin.webshop.dto.LoginRequest;
import hu.bme.crysys.homework.pangolin.webshop.dto.LoginResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.RegistrationRequest;
import hu.bme.crysys.homework.pangolin.webshop.dto.RegistrationResponse;
import hu.bme.crysys.homework.pangolin.webshop.service.AuthService;

@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @NotNull @Valid LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/logout")
    public HttpStatus logout(@RequestHeader(name = "Authorization") String token) {
        return authService.logout(token);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody @NotNull @Valid RegistrationRequest request) {
        return authService.register(request);
    }

}
