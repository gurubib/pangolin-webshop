package hu.bme.crysys.homework.pangolin.webshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static hu.bme.crysys.homework.pangolin.webshop.mapper.FileMapper.*;

import hu.bme.crysys.homework.pangolin.webshop.dto.AddCommentRequest;
import hu.bme.crysys.homework.pangolin.webshop.dto.DownloadResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.SearchResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.UploadRequest;
import hu.bme.crysys.homework.pangolin.webshop.model.Comment;
import hu.bme.crysys.homework.pangolin.webshop.model.File;
import hu.bme.crysys.homework.pangolin.webshop.model.User;
import hu.bme.crysys.homework.pangolin.webshop.repository.CommentRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.FileRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.UserRepository;

@Slf4j
@Service
@PropertySource(name = "configFileProp", value = "application.yml")
public class UserService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final CommentRepository commentRepository;
    private final String filesDirectoryPath;

    public UserService(
            UserRepository userRepository,
            FileRepository fileRepository,
            CommentRepository commentRepository,
            @Value("${pangolin.multimedia.directory.path:./files/}") String filesDirectoryPath
    ) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.commentRepository = commentRepository;
        this.filesDirectoryPath = filesDirectoryPath;
    }

    public SearchResponse search(String fileName) {
        final List<File> files = fileRepository.findByFileNameContaining(fileName);
        final Map<File, List<Comment>> commentMap = files.stream().collect(Collectors.toMap(f -> f, commentRepository::findByFile));
        return mapFilesToSearchResponse(files, commentMap);
    }

    public Optional<DownloadResponse> download(String fileUuid) {
        Optional<File> fileOptional = fileRepository.findByUuid(fileUuid);

        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }

        File file = fileOptional.get();
        try {
            return Optional.of(mapFileToDownloadResponse(file));
        } catch (Exception e) {
            log.debug("Can not read file during parsing the download response - " + e.getMessage());
            return Optional.empty();
        }
    }

    public void upload(UploadRequest request) throws IOException {
        Optional<User> userOptional = userRepository.findByUuid(request.getUploaderUuid());
        User uploader = userOptional.orElseThrow(() -> new NoSuchElementException("Wrong user UUID: " + request.getUploaderUuid()));

        File file = mapUploadRequestToFile(request, filesDirectoryPath);
        file.setUploader(uploader);

        fileRepository.save(file);
    }

    public void addComment(String fileUuid, AddCommentRequest request) {
        final Optional<User> commentingUser = userRepository.findByUuid(request.getUserUuid());
        final Optional<File> fileToAddComment = fileRepository.findByUuid(fileUuid);

        User user = commentingUser.orElseThrow(() -> new NoSuchElementException("Wrong user UUID."));
        File file = fileToAddComment.orElseThrow(() -> new NoSuchElementException("Wrong file UUID."));
        final Comment commentToCreate = createComment(request, user, file);

        commentRepository.save(commentToCreate);
    }

    private Comment createComment(final AddCommentRequest request, final User user, final File file) {
        final Comment comment = new Comment();
        comment.setUuid(UUID.randomUUID().toString());
        comment.setText(request.getText());
        comment.setCreationDate(LocalDateTime.now());
        comment.setUser(user);
        comment.setFile(file);
        return comment;
    }

}
