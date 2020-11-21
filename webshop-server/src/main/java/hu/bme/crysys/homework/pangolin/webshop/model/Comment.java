package hu.bme.crysys.homework.pangolin.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "comment",
        indexes = {
                @Index(name = "comment_uuid_idx", columnList = "uuid", unique = true)
        }
)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 36)
    private String uuid;

    @Column(length = 100)
    private String text;

    private LocalDateTime creationDate;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JoinColumn(name = "file_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private File file;

}
