package hu.bme.crysys.homework.pangolin.webshop.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadResponse {

    private String preview;
    private String fileContentAsString;

}
