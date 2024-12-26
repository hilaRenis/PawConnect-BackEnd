package nl.fontys.pawconnect.business.impl.announcements;

import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.exception.InvalidAnnouncementException;
import nl.fontys.pawconnect.business.impl.converter.AnnouncementConverter;
import nl.fontys.pawconnect.business.impl.validation.LoginValidator;
import nl.fontys.pawconnect.business.interf.announcements.GetAnnouncementUseCase;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.domain.Announcement;
import nl.fontys.pawconnect.domain.requests.announcements.GetAnnouncementRequest;
import nl.fontys.pawconnect.domain.responses.announcements.GetAnnouncementResponse;
import nl.fontys.pawconnect.persistence.interf.AnnouncementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAnnouncementUseCaseImpl implements GetAnnouncementUseCase {
    private final AnnouncementRepository announcementRepository;
    private final LoginValidator loginValidator;
    private final AccessToken accessToken;

    @Override
    @Transactional
    public GetAnnouncementResponse getAnnouncement(GetAnnouncementRequest request, String announcementId) {
        loginValidator.validateToken(accessToken, request.getUserId());

        Optional<Announcement> announcementOptional = announcementRepository.findById(announcementId)
                .map(AnnouncementConverter::convert);
        if(announcementOptional.isEmpty()) {
            throw new InvalidAnnouncementException("ANNOUNCEMENT_ID_INVALID");
        } else {
            return GetAnnouncementResponse.builder()
                    .announcement(announcementOptional.get())
                    .build();
        }
    }
}
