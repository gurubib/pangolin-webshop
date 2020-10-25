package hu.bme.crysys.homework.pangolin.webshop.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

import hu.bme.crysys.homework.pangolin.webshop.dto.AddCommentRequest;
import hu.bme.crysys.homework.pangolin.webshop.dto.DownloadResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.SearchResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.UploadRequest;

@Service
public class UserService {

    public SearchResponse search(String fileName) {
        // TODO - write search
        return SearchResponse.builder().build();
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
