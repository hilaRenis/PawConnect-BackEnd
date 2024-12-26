package nl.fontys.pawconnect.business.interf.announcements;


import nl.fontys.pawconnect.domain.requests.announcements.GetAnnouncementRequest;
import nl.fontys.pawconnect.domain.responses.announcements.GetAnnouncementResponse;

public interface GetAnnouncementUseCase {
    GetAnnouncementResponse getAnnouncement(GetAnnouncementRequest request, String announcementId);
}
