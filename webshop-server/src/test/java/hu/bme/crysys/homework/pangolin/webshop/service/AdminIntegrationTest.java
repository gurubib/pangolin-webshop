package hu.bme.crysys.homework.pangolin.webshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.NotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import hu.bme.crysys.homework.pangolin.webshop.configuration.RoleConfiguration;
import hu.bme.crysys.homework.pangolin.webshop.dto.UpdateUserRequest;
import hu.bme.crysys.homework.pangolin.webshop.model.Comment;
import hu.bme.crysys.homework.pangolin.webshop.model.User;
import hu.bme.crysys.homework.pangolin.webshop.repository.CommentRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.FileRepository;
import hu.bme.crysys.homework.pangolin.webshop.repository.UserRepository;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class AdminIntegrationTest {

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate EXPECTED_DATE_OF_BIRTH = LocalDate.of(2000, 1, 1);
    private static final String EXPECTED_EMAIL = "new_email@email.hu";

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private RoleConfiguration roleConfiguration;

    @MockBean
    private WebSecurityConfiguration webSecurityConfiguration;

    @Test
    void testUpdateUser() throws NotSupportedException {
        User user = saveUser();
        UpdateUserRequest updateUserRequest = createUpdateRequest();

        adminService.updateUser(user.getUuid(), updateUserRequest);

        User userAfterUpdate = userRepository.findByUuid(user.getUuid()).get();
        assertEquals(EXPECTED_DATE_OF_BIRTH, userAfterUpdate.getDateOfBirth());
        assertEquals(EXPECTED_EMAIL, userAfterUpdate.getEmail());
    }

    @Test
    void testUpdateAdminNotAllowed() {
        User admin = userRepository.findByUsername("root").get();
        UpdateUserRequest updateUserRequest = createUpdateRequest();

        assertThrows(NotSupportedException.class, () -> adminService.updateUser(admin.getUuid(), updateUserRequest));
    }

    @Test
    void testDeleteUser() throws NotSupportedException {
        User user = saveUser();

        adminService.removeUser(user.getUuid());

        Optional<User> userAfterUpdate = userRepository.findByUuid(user.getUuid());
        assertTrue(userAfterUpdate.isEmpty());
    }

    @Test
    void testDeleteUserWithComments() throws NotSupportedException {
        User user = saveUser();
        Comment comment = new Comment(1L, "1", "text", LocalDateTime.now(), user, null);
        comment = commentRepository.save(comment);

        adminService.removeUser(user.getUuid());

        Optional<User> userAfterUpdate = userRepository.findByUuid(user.getUuid());
        assertTrue(userAfterUpdate.isEmpty());
        Optional<Comment> commentAfterUserDeleted = commentRepository.findById(comment.getId());
        assertNull(comment.getUser());
    }

    @Test
    void testDeleteAdminNotAllowed() {
        User admin = userRepository.findByUsername("root").get();

        assertThrows(NotSupportedException.class, () -> adminService.removeUser(admin.getUuid()));
    }

    private User saveUser() {
        User user = User.builder()
                .uuid("1")
                .dateOfBirth(NOW)
                .email("email@email.hu")
                .username("user")
                .password("RANDOM_HASH")
                .registrationDate(NOW)
                .build();
        return userRepository.save(user);
    }

    private UpdateUserRequest createUpdateRequest() {
        return UpdateUserRequest.builder()
                .dateOfBirth(EXPECTED_DATE_OF_BIRTH)
                .email(EXPECTED_EMAIL)
                .build();
    }

}
