package nl.fontys.pawconnect.domain.requests.announcements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAnnouncementRequest {
    private String title;
    private String description;
    private List<String> imagesToRemove;
}
