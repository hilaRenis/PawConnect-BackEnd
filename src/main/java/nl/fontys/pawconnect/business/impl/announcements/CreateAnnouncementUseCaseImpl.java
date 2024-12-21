package nl.fontys.pawconnect.business.impl.announcements;

import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.impl.users.LoginValidator;
import nl.fontys.pawconnect.business.interf.announcements.CreateAnnouncementUseCase;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.domain.requests.announcements.CreateAnnouncementRequest;
import nl.fontys.pawconnect.domain.responses.announcements.CreateAnnouncementResponse;
import nl.fontys.pawconnect.persistence.entity.AnnouncementEntity;
import nl.fontys.pawconnect.persistence.interf.AnnouncementRepository;
import nl.fontys.pawconnect.persistence.interf.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@AllArgsConstructor
public class CreateAnnouncementUseCaseImpl implements CreateAnnouncementUseCase {
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;

    private AccessToken accessToken;

    private final LoginValidator loginValidator;

    @Override
    @Transactional
    public CreateAnnouncementResponse createAnnouncement(CreateAnnouncementRequest request) {
        loginValidator.validateToken(accessToken, request.getUserId());

        AnnouncementEntity savedAnnouncement = saveNewAnnouncement(request);
        return CreateAnnouncementResponse.builder()
                .announcementUUID(savedAnnouncement.getId())
                .build();
    }

    private AnnouncementEntity saveNewAnnouncement(CreateAnnouncementRequest request) {
        AnnouncementEntity newAnnouncement = AnnouncementEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dateMade(Date.from(Instant.now()))
                .announcer(userRepository.findById(request.getUserId()).get())
                .referencedPostUUID(request.getReferencedPostUUID())
                .build();
        return announcementRepository.save(newAnnouncement);
    }


}
