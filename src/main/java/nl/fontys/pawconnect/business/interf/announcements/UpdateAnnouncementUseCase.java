package nl.fontys.pawconnect.business.interf.announcements;

import nl.fontys.pawconnect.domain.requests.announcements.UpdateAnnouncementRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UpdateAnnouncementUseCase {
    void updateAnnouncement(String announcementId, UpdateAnnouncementRequest request, List<MultipartFile> multipartFiles);
}
