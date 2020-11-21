package hu.bme.crysys.homework.pangolin.webshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.crysys.homework.pangolin.webshop.controller.interfaces.IAuthController;
import hu.bme.crysys.homework.pangolin.webshop.dto.LoginRequest;
import hu.bme.crysys.homework.pangolin.webshop.dto.LoginResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.RegistrationRequest;
import hu.bme.crysys.homework.pangolin.webshop.dto.RegistrationResponse;
import hu.bme.crysys.homework.pangolin.webshop.service.AuthService;

@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements IAuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        return authService.login(request);
    }

    @Override
    public HttpStatus logout(String token) {
        return authService.logout(token);
    }

    @Override
    public ResponseEntity<RegistrationResponse> register(RegistrationRequest request) {
        return authService.register(request);
    }

}
