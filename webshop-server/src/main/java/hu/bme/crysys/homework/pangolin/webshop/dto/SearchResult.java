package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {

    // TODO - containment of the search result add missing fields below
    private String fileName;
    private String location;
    private String uploaderUserName;
    private List<CommentResult> comments;

}
