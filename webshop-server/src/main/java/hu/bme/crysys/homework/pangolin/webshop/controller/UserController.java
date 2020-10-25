package hu.bme.crysys.homework.pangolin.webshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.Optional;

import hu.bme.crysys.homework.pangolin.webshop.dto.*;
import hu.bme.crysys.homework.pangolin.webshop.service.UserService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/search/{fileName}")
    public ResponseEntity<SearchResponse> search(@PathVariable(name = "fileName") String fileName) {
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

    @GetMapping("/files/{id}")
    public ResponseEntity<DownloadResponse> download(@PathVariable(name = "id") @NotNull String id) {
        Optional<DownloadResponse> downloadResponse = userService.download(id);

        if (downloadResponse.isEmpty()) {
            log.debug("Download: id(" + id + ") not found. --> 404 - Not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.debug("Download: file. --> 200 - Ok");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(downloadResponse.get());
        }
    }

    @PostMapping("/files")
    public ResponseEntity upload(@NotNull UploadRequest request) {
        boolean isSuccess = userService.upload(request);

        if (isSuccess) {
            log.debug("Upload: done. --> 200 - Ok");
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            log.debug("Upload: error. --> 500 - Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/files/{id}/comments")
    public ResponseEntity addComment(@PathVariable(name = "id") @NotNull String id, @NotNull @Valid AddCommentRequest request) {
        boolean isSuccess = userService.addComment(id, request);

        if (isSuccess) {
            log.debug("Add Comment: done. --> 200 - Ok");
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            log.debug("Add Comment: error. --> 500 - Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
