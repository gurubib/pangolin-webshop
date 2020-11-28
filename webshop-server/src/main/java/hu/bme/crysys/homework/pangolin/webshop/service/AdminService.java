package hu.bme.crysys.homework.pangolin.webshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import hu.bme.crysys.homework.pangolin.webshop.configuration.RoleConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.dto.ListUsersResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.UpdateUserRequest;
import hu.bme.crysys.homework.pangolin.webshop.dto.UserResult;
import hu.bme.crysys.homework.pangolin.webshop.mapper.UserMapper;
import hu.bme.crysys.homework.pangolin.webshop.model.Comment;
import hu.bme.crysys.homework.pangolin.webshop.model.File;
import hu.bme.crysys.homework.pangolin.webshop.model.User;
import hu.bme.crysys.homework.pangolin.webshop.repository.CommentRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.FileRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final CommentRepository commentRepository;
    private final RoleConfiguration roleConfiguration;

    public void updateUser(String userUuid, UpdateUserRequest request) throws NotSupportedException {
        User user = getUser(userUuid);

        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getEmail() != null && request.getEmail().contains("@")) {
            user.setEmail(request.getEmail());
        }

        userRepository.save(user);
    }

    public void removeUser(String userUuid) throws NotSupportedException {
        User user = getUser(userUuid);
        user = makeFilesIndependentFromUser(user);
        user = makeCommentsIndependentFromUser(user);
        userRepository.delete(user);
    }

    private User getUser(String userUuid) throws NotSupportedException {
        Optional<User> userOptional = userRepository.findByUuid(userUuid);
        User user = userOptional.orElseThrow(() -> new NoSuchElementException("Wrong user UUID: " + userUuid));
        if (isUserAdmin(user)) {
            throw new NotSupportedException("The user to handle has admin role. This action is not supported.");
        }
        return user;
    }

    private boolean isUserAdmin(User user) {
        return user.getRoles().stream().anyMatch(r -> r.equals(roleConfiguration.getAdmin()));
    }

    private User makeFilesIndependentFromUser(User user) {
        List<File> files = fileRepository.findByUploader(user);
        files.forEach(f -> {
            f.setUploader(null);
            fileRepository.save(f);
        });
        user.setFiles(new ArrayList<>());
        return userRepository.save(user);
    }

    private User makeCommentsIndependentFromUser(User user) {
        List<Comment> comments = commentRepository.findByUser(user);
        comments.forEach(c -> {
            c.setUser(null);
            commentRepository.save(c);
        });
        user.setComments(new ArrayList<>());
        return userRepository.save(user);
    }

    public ListUsersResponse listUsers() {
        final List<User> users = userRepository.findAll();
        final List<UserResult> userResults = UserMapper.mapUsersToUserResults(users);

        return new ListUsersResponse(userResults);
    }

}
