package nl.fontys.pawconnect.business.interf.announcements;

import nl.fontys.pawconnect.domain.requests.announcements.CreateAnnouncementRequest;
import nl.fontys.pawconnect.domain.responses.announcements.CreateAnnouncementResponse;

public interface CreateAnnouncementUseCase {
    CreateAnnouncementResponse createAnnouncement(CreateAnnouncementRequest request);
}
