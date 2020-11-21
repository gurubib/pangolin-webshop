package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    @NotNull
    private String email;

    @NotNull
    @Size(min = 8, max = 32)
    private String password;

    @NotNull
    @Size(min = 8, max = 32)
    private String passwordConfirm;

    @NotNull
    @Size(min = 4, max = 32)
    private String username;

    @NotNull
    private LocalDate dateOfBirth;

}
