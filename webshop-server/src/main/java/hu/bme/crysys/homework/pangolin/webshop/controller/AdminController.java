package hu.bme.crysys.homework.pangolin.webshop.controller;

import hu.bme.crysys.homework.pangolin.webshop.controller.interfaces.IAdminController;
import hu.bme.crysys.homework.pangolin.webshop.dto.ListUsersResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.UpdateUserRequest;
import hu.bme.crysys.homework.pangolin.webshop.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController implements IAdminController {

    private final AdminService adminService;

    @Override
    public ResponseEntity<?> updateUser(String userUuid, UpdateUserRequest request) {
        try {
            adminService.updateUser(userUuid, request);

            log.debug("Update User: done. --> 204 - Ok");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.debug("Update User: error. --> 500 - Internal server error");
            log.debug("Message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<?> removeUser(String userUuid) {
        try {
            adminService.removeUser(userUuid);

            log.debug("Remove User: done. --> 204 - Ok");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.debug("Remove User: error. --> 500 - Internal server error");
            log.debug("Message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<?> listUsers() {
        final ListUsersResponse listUsersResponse = adminService.listUsers();

        log.debug("List users: results. --> 200 - Ok");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listUsersResponse);
    }

}
