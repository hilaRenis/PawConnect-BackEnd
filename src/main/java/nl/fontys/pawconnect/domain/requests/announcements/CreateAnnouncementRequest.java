package nl.fontys.pawconnect.domain.requests.announcements;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnnouncementRequest {
    @NotBlank
    private String userId;
    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "title")
    private String title;
    @NotBlank
    @Length(min = 3, max = 1000)
    @Column(name = "email")
    private String description;
    private String referencedPostUUID;
}
