package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequest {

    // TODO - write content (remove below field)
    @NotNull
    private String fieldToRemove;

}
