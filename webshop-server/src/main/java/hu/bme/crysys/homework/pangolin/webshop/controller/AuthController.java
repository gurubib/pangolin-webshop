package hu.bme.crysys.homework.pangolin.webshop.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))})
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @NotNull @Valid LoginRequest request) {
        return authService.login(request);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "403")
    })
    @GetMapping("/logout")
    public HttpStatus logout(@RequestHeader(name = "Authorization") String token) {
        return authService.logout(token);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationResponse.class))})
    })
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody @NotNull @Valid RegistrationRequest request) {
        return authService.register(request);
    }

}
