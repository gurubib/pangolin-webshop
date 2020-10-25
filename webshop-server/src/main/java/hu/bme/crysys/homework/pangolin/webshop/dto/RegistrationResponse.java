package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {

    private AuthStatus status;

}
