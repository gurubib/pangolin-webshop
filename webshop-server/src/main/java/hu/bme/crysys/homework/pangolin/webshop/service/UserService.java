package hu.bme.crysys.homework.pangolin.webshop.service;

import hu.bme.crysys.homework.pangolin.webshop.dto.*;
import hu.bme.crysys.homework.pangolin.webshop.model.File;
import hu.bme.crysys.homework.pangolin.webshop.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static hu.bme.crysys.homework.pangolin.webshop.mapper.FileMapper.filesToSearchResults;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final FileRepository fileRepository;

    public SearchResponse search(String fileName) {
        final List<File> files = fileRepository.findByFileNameContaining(fileName);
        final List<SearchResult> searchResults = filesToSearchResults(files);
        return SearchResponse.builder()
                .results(searchResults)
                .build();
    }

    public Optional<DownloadResponse> download(String fileId) {
        // TODO - write download
        return Optional.empty();
    }

    public boolean upload(UploadRequest request) {
        // TODO - write upload
        return true;
    }

    public boolean addComment(String fileId, AddCommentRequest request) {
        // TODO - write addComment
        return true;
    }

}
