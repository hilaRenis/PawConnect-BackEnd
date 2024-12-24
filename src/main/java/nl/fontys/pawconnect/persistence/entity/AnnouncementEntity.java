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
    @Column(name = "email")
    private String description;

    @NotNull
    @Column(name = "date_made")
    private Date dateMade;

    @ElementCollection // To store the list of URLs as a separate table
    @CollectionTable(name = "announcement_images", joinColumns = @JoinColumn(name = "announcement_id"))
    @Column(name = "image_url")
    private List<String> imageUrls; // New field

    @ManyToOne
    @JoinColumn(name = "announcer_id", referencedColumnName = "id")
    @NotNull
    private UserEntity announcer;
}
