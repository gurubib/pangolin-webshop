package hu.bme.crysys.homework.pangolin.webshop.controller.interfaces;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import hu.bme.crysys.homework.pangolin.webshop.dto.UpdateUserRequest;

@Validated
@RestController
public interface IAdminController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "403", description = "Authentication required"),
            @ApiResponse(responseCode = "500")
    })
    @RolesAllowed("ADMIN")
    @PutMapping("/users/{uuid}")
    ResponseEntity<?> updateUser(
            @PathVariable(name = "uuid") @NotNull String userUuid,
            @RequestBody @NotNull @Valid UpdateUserRequest request
    );


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "403", description = "Authentication required"),
            @ApiResponse(responseCode = "500")
    })
    @RolesAllowed("ADMIN")
    @DeleteMapping("/users/{uuid}")
    ResponseEntity<?> removeUser(@PathVariable(name = "uuid") @NotNull String userUuid);

}
