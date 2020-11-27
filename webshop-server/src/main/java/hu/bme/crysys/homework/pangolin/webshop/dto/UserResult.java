package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResult {

    private String uuid;
    private String username;
    private String email;
    private LocalDate dateOfBirth;
    private LocalDate registrationDate;

}
