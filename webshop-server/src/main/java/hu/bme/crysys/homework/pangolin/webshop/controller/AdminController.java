package hu.bme.crysys.homework.pangolin.webshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.crysys.homework.pangolin.webshop.controller.interfaces.IAdminController;
import hu.bme.crysys.homework.pangolin.webshop.dto.UpdateUserRequest;
import hu.bme.crysys.homework.pangolin.webshop.service.AdminService;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController implements IAdminController {

    private final AdminService adminService;

    @Override
    public HttpStatus updateUser(String userUuid, UpdateUserRequest request) {
        boolean isSuccess = adminService.updateUser(userUuid, request);

        if (isSuccess) {
            log.debug("Update User: done. --> 204 - Ok");
            return HttpStatus.NO_CONTENT;
        } else {
            log.debug("Update User: error. --> 500 - Internal server error");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public HttpStatus removeUser(String userUuid) {
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
