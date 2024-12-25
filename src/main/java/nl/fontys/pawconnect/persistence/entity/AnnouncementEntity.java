package nl.fontys.pawconnect.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

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
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "date_made")
    private Date dateMade;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Treated as OneToMany
    @JoinTable(
            name = "announcement_images",
            joinColumns = @JoinColumn(name = "announcement_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<ImageEntity> images;

    @ManyToOne
    @JoinColumn(name = "announcer_id", referencedColumnName = "id")
    @NotNull
    private UserEntity announcer;
}
