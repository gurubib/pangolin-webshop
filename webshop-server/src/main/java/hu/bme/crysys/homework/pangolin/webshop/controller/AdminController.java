package hu.bme.crysys.homework.pangolin.webshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @RolesAllowed("ADMIN")
    @PutMapping("/admin/users/{id}")
    public ResponseEntity updateUser(@PathVariable(name = "id") @NotNull String id, @NotNull @Valid UpdateUserRequest request) {
        boolean isSuccess = adminService.updateUser(id, request);

        if (isSuccess) {
            log.debug("Update User: done. --> 200 - Ok");
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            log.debug("Update User: error. --> 500 - Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity removeUser(@PathVariable(name = "id") @NotNull String id) {
        boolean isSuccess = adminService.removeUser(id);

        if (isSuccess) {
            log.debug("Remove User: done. --> 200 - Ok");
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            log.debug("Remove User: error. --> 500 - Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
