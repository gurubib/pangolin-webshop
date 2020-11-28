package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {

    private String uuid;
    private String filename;
    private String uploaderUserName;
    private LocalDateTime date;
    private List<CommentResult> comments;
    private String preview;

}
