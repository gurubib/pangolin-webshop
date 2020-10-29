package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResult {

    // TODO - write missing fields
    private String text;
    private String userName;
    private LocalDate date;

}