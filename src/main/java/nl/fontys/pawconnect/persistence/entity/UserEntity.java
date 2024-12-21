package nl.fontys.pawconnect.persistence.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@EqualsAndHashCode
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Length(min = 3, max = 75)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @Nullable
    @Length(min = 3, max = 110)
    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToMany(mappedBy = "announcer", cascade = CascadeType.ALL)
    private List<AnnouncementEntity> announcements;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<AccountEntity> accounts;
//
//    @OneToMany(mappedBy = "user")
//    private List<FriendshipEntity> friendships;
}
