package nl.fontys.pawconnect.domain.requests.announcements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetVisibleAnnouncementsRequest {
    private String userId;
}
