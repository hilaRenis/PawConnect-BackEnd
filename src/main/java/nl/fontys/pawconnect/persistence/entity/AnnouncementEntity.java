package nl.fontys.pawconnect.persistence.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Entity
@Table(name = "announcements")
@EqualsAndHashCode
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "title")
    private String title;

    @NotBlank
    @Length(min = 3, max = 1000)
    @Column(name = "email")
    private String description;

    @NotNull
    @Column(name = "date_made")
    private Date dateMade;

    @Nullable
    @Column(name = "reference_post_id")
    private String referencedPostUUID;

    @ManyToOne
    @JoinColumn(name = "announcer_id", referencedColumnName = "id")
    @NotNull
    private UserEntity announcer;
}
