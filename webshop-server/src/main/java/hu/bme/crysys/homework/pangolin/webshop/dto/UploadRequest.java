package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequest {

    @NotNull
    private String uploaderUuid;

    @NotNull
    private String requiredFileName;

    @NotNull
    private String fileContentAsString;

    @NotNull
    private FileType fileType;

}
