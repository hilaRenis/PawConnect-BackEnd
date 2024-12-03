package nl.fontys.pawconnect.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.validator.constraints.Length;
import lombok.*;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@EqualsAndHashCode
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

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
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRoles role;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<AccountEntity> accounts;
//
//    @OneToMany(mappedBy = "user")
//    private List<FriendshipEntity> friendships;
}
