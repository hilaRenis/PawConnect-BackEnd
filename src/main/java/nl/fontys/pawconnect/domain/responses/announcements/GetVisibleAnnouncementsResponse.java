package nl.fontys.pawconnect.domain.responses.announcements;

import lombok.Builder;
import lombok.Data;
import nl.fontys.pawconnect.domain.Announcement;

import java.util.List;

@Data
@Builder
public class GetVisibleAnnouncementsResponse {
    private List<Announcement> announcements;
}
