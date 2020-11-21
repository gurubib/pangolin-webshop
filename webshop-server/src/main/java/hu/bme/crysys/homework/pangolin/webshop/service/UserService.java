package hu.bme.crysys.homework.pangolin.webshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static hu.bme.crysys.homework.pangolin.webshop.mapper.FileMapper.mapFileToDownloadResponse;
import static hu.bme.crysys.homework.pangolin.webshop.mapper.FileMapper.mapFilesToSearchResponse;

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
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final CommentRepository commentRepository;

    public SearchResponse search(String fileName) {
        final List<File> files = fileRepository.findByFileNameContaining(fileName);
        return mapFilesToSearchResponse(files);
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

    public boolean upload(UploadRequest request) {
        // TODO - write upload
        return true;
    }

    public boolean addComment(String fileUuid, AddCommentRequest request) {
        final Optional<User> commentingUser = userRepository.findByUuid(request.getUserUuid());
        final Optional<File> fileToAddComment = fileRepository.findByUuid(fileUuid);

        if (commentingUser.isEmpty() || fileToAddComment.isEmpty()) {
            return false;
        }

        final Comment commentToCreate = createComment(request, commentingUser.get(), fileToAddComment.get());
        commentRepository.save(commentToCreate);

        return true;
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
