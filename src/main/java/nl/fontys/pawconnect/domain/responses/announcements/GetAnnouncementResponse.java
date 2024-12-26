package nl.fontys.pawconnect.domain.responses.announcements;

import lombok.Builder;
import lombok.Data;
import nl.fontys.pawconnect.domain.Announcement;

@Data
@Builder
public class GetAnnouncementResponse {
    private Announcement announcement;
}
