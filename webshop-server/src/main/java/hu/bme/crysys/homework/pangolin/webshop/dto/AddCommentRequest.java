package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentRequest {

    @NotNull
    @Size(max = 100)
    private String text;

    @NotNull
    private String userUuid;
}
