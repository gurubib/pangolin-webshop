package hu.bme.crysys.homework.pangolin.webshop.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import hu.bme.crysys.homework.pangolin.webshop.dto.UpdateUserRequest;
import hu.bme.crysys.homework.pangolin.webshop.service.AdminService;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "403", description = "Authentication required"),
            @ApiResponse(responseCode = "500")
    })
    @RolesAllowed("ADMIN")
    @PutMapping("/users/{uuid}")
    public HttpStatus updateUser(
            @PathVariable(name = "uuid") @NotNull String userUuid,
            @RequestBody @NotNull @Valid UpdateUserRequest request
    ) {
        boolean isSuccess = adminService.updateUser(userUuid, request);

        if (isSuccess) {
            log.debug("Update User: done. --> 204 - Ok");
            return HttpStatus.NO_CONTENT;
        } else {
            log.debug("Update User: error. --> 500 - Internal server error");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "403", description = "Authentication required"),
            @ApiResponse(responseCode = "500")
    })
    @RolesAllowed("ADMIN")
    @DeleteMapping("/users/{uuid}")
    public HttpStatus removeUser(@PathVariable(name = "uuid") @NotNull String userUuid) {
        boolean isSuccess = adminService.removeUser(userUuid);

        if (isSuccess) {
            log.debug("Remove User: done. --> 204 - Ok");
            return HttpStatus.NO_CONTENT;
        } else {
            log.debug("Remove User: error. --> 500 - Internal server error");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
