package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private AuthStatus status;
    private String username;
    private String uuid;
    private String token;

}
