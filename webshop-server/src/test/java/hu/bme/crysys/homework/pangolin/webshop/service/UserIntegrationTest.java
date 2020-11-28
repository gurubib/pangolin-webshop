package hu.bme.crysys.homework.pangolin.webshop.service;

import hu.bme.crysys.homework.pangolin.webshop.dto.*;
import hu.bme.crysys.homework.pangolin.webshop.model.Comment;
import hu.bme.crysys.homework.pangolin.webshop.model.File;
import hu.bme.crysys.homework.pangolin.webshop.model.User;
import hu.bme.crysys.homework.pangolin.webshop.repository.CommentRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.FileRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserIntegrationTest {

    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final String PREVIOUSLY_UPLOADED_FILENAME = "test.caff";

    @MockBean
    private WebSecurityConfiguration webSecurityConfiguration;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Test
    public void testSearch() {
        final String fileUuid = UUID.randomUUID().toString();
        savePreviouslyUploadedFile(PREVIOUSLY_UPLOADED_FILENAME, fileUuid);

        final SearchResponse response = userService.search("test.caff");
        final List<SearchResult> searchResults = response.getResults();

        assertEquals(1, searchResults.size());

        final SearchResult result = searchResults.get(0);
        assertEquals(PREVIOUSLY_UPLOADED_FILENAME, result.getFilename());
        assertNotNull(result.getPreview());
    }

    private void savePreviouslyUploadedFile(final String filename, final String uuid) {
        final File file = File.builder()
                .uuid(uuid)
                .fileName(filename)
                .location("./files-test/")
                .creationDate(NOW)
                .comments(emptyList())
                .build();

        fileRepository.save(file);
    }

    @Test
    public void testUpload() throws IOException {
        final User user = saveUser();
        final UploadRequest request = createUploadRequest(user.getUuid());

        userService.upload(request);

        assertTrue(new java.io.File("./files-test/test-upload.caff").exists());

        final List<File> uploadedFile = fileRepository.findByFileNameContaining(request.getRequiredFileName());
        assertEquals(1, uploadedFile.size());

        Files.delete(Path.of("./files-test/test-upload.caff"));
    }

    private User saveUser() {
        User user = User.builder()
                .uuid(UUID.randomUUID().toString())
                .dateOfBirth(LocalDate.now())
                .email("email@email.hu")
                .username("user")
                .password("RANDOM_HASH")
                .registrationDate(LocalDate.now())
                .build();

        return userRepository.save(user);
    }

    private UploadRequest createUploadRequest(final String uploaderUuid) {
        return UploadRequest.builder()
                .uploaderUuid(uploaderUuid)
                .requiredFileName("test-upload")
                .fileContentAsString("cmFuZG9tLWZpbGUtY29udGVudA==")
                .fileType(FileType.CAFF)
                .build();
    }

    @Test
    public void testDownload() {
        final String fileUuid = UUID.randomUUID().toString();
        savePreviouslyUploadedFile(PREVIOUSLY_UPLOADED_FILENAME, fileUuid);

        final DownloadResponse response = userService.download(fileUuid)
                .orElseThrow(() -> new IllegalArgumentException("There is no file with this uuid!"));

        assertFalse(response.getPreview().isEmpty());
        assertFalse(response.getPreview().isEmpty());
    }

    @Test
    public void testAddComment() {
        final User user = saveUser();
        final String fileUuid = UUID.randomUUID().toString();
        savePreviouslyUploadedFile(PREVIOUSLY_UPLOADED_FILENAME, fileUuid);
        final AddCommentRequest addCommentRequest = createAddCommentRequest(user.getUuid());

        userService.addComment(fileUuid, addCommentRequest);

        final List<Comment> comments = commentRepository.findByUser(user);

        assertEquals(1, comments.size());

        final Comment comment = comments.get(0);
        assertEquals(fileUuid, comment.getFile().getUuid());
    }

    private AddCommentRequest createAddCommentRequest(final String userUuid) {
        return AddCommentRequest.builder()
                .text("Comment text")
                .userUuid(userUuid)
                .build();
    }

}
