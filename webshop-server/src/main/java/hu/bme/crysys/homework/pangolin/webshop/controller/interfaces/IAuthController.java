package hu.bme.crysys.homework.pangolin.webshop.controller.interfaces;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Validated
@RestController
public interface IAuthController {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = LoginResponse.class
                                    ))
                    }),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = LoginResponse.class
                                    ))
                    })
    })
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody @NotNull @Valid LoginRequest request);


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400")
    })
    @GetMapping("/logout")
    HttpStatus logout(@RequestHeader(name = "Authorization") String token);


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registration successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = RegistrationResponse.class
                                    ))
                    }),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = RegistrationResponse.class
                                    ))
                    })
    })
    @PostMapping("/register")
    ResponseEntity<RegistrationResponse> register(@RequestBody @NotNull @Valid RegistrationRequest request);

}
