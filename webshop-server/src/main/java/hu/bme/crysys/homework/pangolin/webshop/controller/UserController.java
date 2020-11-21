package hu.bme.crysys.homework.pangolin.webshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import hu.bme.crysys.homework.pangolin.webshop.controller.interfaces.IUserController;
import hu.bme.crysys.homework.pangolin.webshop.dto.AddCommentRequest;
import hu.bme.crysys.homework.pangolin.webshop.dto.DownloadResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.SearchResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.UploadRequest;
import hu.bme.crysys.homework.pangolin.webshop.service.UserService;

@Slf4j
@Validated
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class UserController implements IUserController {

    private final UserService userService;

    @Override
    public ResponseEntity<SearchResponse> search(String fileName) {
        SearchResponse searchResponse = userService.search(fileName);

        if (searchResponse.getResults().isEmpty()) {
            log.debug("Search: empty results list. --> 404 - Not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.debug("Search: results. --> 200 - Ok");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(searchResponse);
        }
    }

    @Override
    public ResponseEntity<DownloadResponse> download(String fileUuid) {
        Optional<DownloadResponse> downloadResponse = userService.download(fileUuid);

        if (downloadResponse.isEmpty()) {
            log.debug("Download: uuid(" + fileUuid + ") not found. --> 404 - Not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.debug("Download: file. --> 200 - Ok");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(downloadResponse.get());
        }
    }

    @Override
    public HttpStatus upload(UploadRequest request) {
        boolean isSuccess = userService.upload(request);

        if (isSuccess) {
            log.debug("Upload: done. --> 204 - Ok");
            return HttpStatus.NO_CONTENT;
        } else {
            log.debug("Upload: error. --> 500 - Internal server error");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public HttpStatus addComment(String fileUuid, AddCommentRequest request) {
        boolean isSuccess = userService.addComment(fileUuid, request);

        if (isSuccess) {
            log.debug("Add Comment: done. --> 204 - Ok");
            return HttpStatus.NO_CONTENT;
        } else {
            log.debug("Add Comment: error. --> 500 - Internal server error");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
