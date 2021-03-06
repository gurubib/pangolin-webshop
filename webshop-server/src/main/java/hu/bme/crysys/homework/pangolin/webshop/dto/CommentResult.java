package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResult {

    private String uuid;
    private String text;
    private String userName;
    private LocalDateTime date;

}
