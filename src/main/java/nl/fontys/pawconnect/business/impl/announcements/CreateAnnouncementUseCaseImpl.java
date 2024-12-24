package nl.fontys.pawconnect.business.impl.announcements;

import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.impl.validation.LoginValidator;
import nl.fontys.pawconnect.business.interf.AmazonFileService;
import nl.fontys.pawconnect.business.interf.announcements.CreateAnnouncementUseCase;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.domain.requests.announcements.CreateAnnouncementRequest;
import nl.fontys.pawconnect.domain.responses.announcements.CreateAnnouncementResponse;
import nl.fontys.pawconnect.persistence.entity.AnnouncementEntity;
import nl.fontys.pawconnect.persistence.interf.AnnouncementRepository;
import nl.fontys.pawconnect.persistence.interf.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateAnnouncementUseCaseImpl implements CreateAnnouncementUseCase {
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;

    private AccessToken accessToken;

    private final LoginValidator loginValidator;

    private AmazonFileService amazonFileService;

    @Override
    @Transactional
    public CreateAnnouncementResponse createAnnouncement(List<MultipartFile> images, CreateAnnouncementRequest request) {
        loginValidator.validateToken(accessToken, request.getUserId());

        List<String> amazonFilePaths = new ArrayList<>();
        for(MultipartFile file : images) {
            String amazonFilePath = amazonFileService.uploadFile(file, "announcements/" + file.getOriginalFilename());
            amazonFilePaths.add(amazonFilePath);
        }

        AnnouncementEntity savedAnnouncement = saveNewAnnouncement(request, amazonFilePaths);
        return CreateAnnouncementResponse.builder()
                .announcementUUID(savedAnnouncement.getId())
                .build();
    }

    private AnnouncementEntity saveNewAnnouncement(CreateAnnouncementRequest request, List<String> filePaths) {
        AnnouncementEntity newAnnouncement = AnnouncementEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dateMade(Date.from(Instant.now()))
                .announcer(userRepository.findById(request.getUserId()).get())
                .imageUrls(filePaths)
                .build();
        return announcementRepository.save(newAnnouncement);
    }
}
