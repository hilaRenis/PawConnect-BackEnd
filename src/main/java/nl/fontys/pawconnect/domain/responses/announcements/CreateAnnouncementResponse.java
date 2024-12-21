package nl.fontys.pawconnect.domain.responses.announcements;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAnnouncementResponse {
    private String announcementUUID;
}
