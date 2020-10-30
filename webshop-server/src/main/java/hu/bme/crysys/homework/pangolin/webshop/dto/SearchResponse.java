package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    private List<SearchResult> results;

}
