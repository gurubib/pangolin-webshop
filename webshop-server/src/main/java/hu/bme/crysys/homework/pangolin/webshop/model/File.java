package hu.bme.crysys.homework.pangolin.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "file",
        indexes = {
                @Index(name = "file_fileName_idx", columnList = "fileName")
        }
)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String location;

    private LocalDate creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "file")
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User uploader;

}
