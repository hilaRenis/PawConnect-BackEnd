package nl.fontys.pawconnect.business.interf.announcements;

import nl.fontys.pawconnect.domain.requests.announcements.CreateAnnouncementRequest;
import nl.fontys.pawconnect.domain.responses.announcements.CreateAnnouncementResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CreateAnnouncementUseCase {
    CreateAnnouncementResponse createAnnouncement(List<MultipartFile> images, CreateAnnouncementRequest request);
}
