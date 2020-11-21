package hu.bme.crysys.homework.pangolin.webshop.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "user",
        indexes = {
                @Index(name = "user_uuid_idx", columnList = "uuid", unique = true)
        }
)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 36)
    private String uuid;

    @NotEmpty
    private String username;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private LocalDate dateOfBirth;

    @NotEmpty
    private LocalDate registrationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uploader")
    private List<File> files = new ArrayList<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
