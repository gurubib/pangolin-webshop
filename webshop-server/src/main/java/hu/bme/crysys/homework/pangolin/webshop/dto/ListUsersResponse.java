package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListUsersResponse {

    private List<UserResult> users = new ArrayList<>();

}
