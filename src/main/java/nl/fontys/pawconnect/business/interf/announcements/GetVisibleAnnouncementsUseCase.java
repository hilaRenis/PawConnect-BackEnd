package nl.fontys.pawconnect.business.interf.announcements;


import nl.fontys.pawconnect.domain.requests.announcements.GetVisibleAnnouncementsRequest;
import nl.fontys.pawconnect.domain.responses.announcements.GetVisibleAnnouncementsResponse;

public interface GetVisibleAnnouncementsUseCase {
    GetVisibleAnnouncementsResponse getVisibleAnnouncements(GetVisibleAnnouncementsRequest request);
}
