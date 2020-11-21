package hu.bme.crysys.homework.pangolin.webshop.controller.interfaces;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import hu.bme.crysys.homework.pangolin.webshop.dto.AddCommentRequest;
import hu.bme.crysys.homework.pangolin.webshop.dto.DownloadResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.SearchResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.UploadRequest;

@Validated
@RestController
public interface IUserController {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SearchResponse.class
                                    ))
                    }),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SearchResponse.class
                                    ))
                    })
    })
    @GetMapping("/search/{fileName}")
    ResponseEntity<SearchResponse> search(@PathVariable(name = "fileName") String fileName);


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DownloadResponse.class
                                    ))
                    }),
            @ApiResponse(responseCode = "403", description = "Authentication required"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DownloadResponse.class
                                    ))
                    })
    })
    @GetMapping("/{uuid}")
    ResponseEntity<DownloadResponse> download(@PathVariable(name = "uuid") @NotNull String fileUuid);


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "403", description = "Authentication required"),
            @ApiResponse(responseCode = "500")
    })
    @PostMapping
    ResponseEntity<?> upload(@RequestBody @NotNull @Valid UploadRequest request);


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "403", description = "Authentication required"),
            @ApiResponse(responseCode = "500")
    })
    @PostMapping("/{uuid}/comments")
    ResponseEntity<?> addComment(
            @PathVariable(name = "uuid") @NotNull String fileUuid,
            @RequestBody @NotNull @Valid AddCommentRequest request
    );

}
