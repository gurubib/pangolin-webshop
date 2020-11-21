package hu.bme.crysys.homework.pangolin.webshop.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "file",
        indexes = {
                @Index(name = "file_fileName_idx", columnList = "fileName"),
                @Index(name = "file_uuid_idx", columnList = "uuid", unique = true)
        }
)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 36)
    private String uuid;

    private String fileName;

    private String location;

    private LocalDateTime creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "file")
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User uploader;

}
