package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    // TODO - write missing fields
    @NotNull
    private String username;

}
