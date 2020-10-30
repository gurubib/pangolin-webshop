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

    // TODO - containment of the search result add missing fields below
    private String uuid;
    private String fileName;
    private String location;
    private String uploaderUserName;
    private LocalDateTime date;
    private List<CommentResult> comments;

}
