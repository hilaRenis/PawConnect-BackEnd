package nl.fontys.pawconnect.business.impl.announcements;

import lombok.RequiredArgsConstructor;
import nl.fontys.pawconnect.business.exception.*;
import nl.fontys.pawconnect.business.impl.validation.LoginValidator;
import nl.fontys.pawconnect.business.interf.AmazonFileService;
import nl.fontys.pawconnect.business.interf.announcements.UpdateAnnouncementUseCase;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.domain.requests.announcements.UpdateAnnouncementRequest;
import nl.fontys.pawconnect.persistence.entity.AnnouncementEntity;
import nl.fontys.pawconnect.persistence.entity.ImageEntity;
import nl.fontys.pawconnect.persistence.interf.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateAnnouncementUseCaseImpl implements UpdateAnnouncementUseCase {
    private final AnnouncementRepository announcementRepository;
    private final AccessToken accessToken;
    private final AmazonFileService amazonFileService;
    private final LoginValidator loginValidator;

    @Value("${aws.s3.bucket.link}")
    private String s3BucketLink;

    @Override
    @Transactional
    public void updateAnnouncement(String announcementId, UpdateAnnouncementRequest request, List<MultipartFile> multipartFiles) {
        Optional<AnnouncementEntity> announcementOptional = announcementRepository.findById(announcementId);
        if(announcementOptional.isEmpty()) {
            throw new InvalidAnnouncementException("ANNOUNCEMENT_ID_INVALID");
        }
        AnnouncementEntity announcement = announcementOptional.get();
        loginValidator.validateToken(accessToken, announcement.getAnnouncer().getId());

        boolean fieldsUpdated = false;
        boolean imagesDeleted = false;
        boolean imagesAdded = false;
        if(request != null) {
            fieldsUpdated = updateFields(request, announcement);
            imagesDeleted = deleteImages(request.getImagesToRemove(), announcement);
        }

        if(multipartFiles != null && !multipartFiles.isEmpty()) {
            imagesAdded = addImages(multipartFiles, announcement);
        }

        if (fieldsUpdated || imagesDeleted || imagesAdded) {
            announcementRepository.save(announcement);
        }
    }

    private boolean updateFields(UpdateAnnouncementRequest request, AnnouncementEntity announcement){
        boolean changed = false;

        if(request.getTitle() != null){
            announcement.setTitle(request.getTitle());
            changed = true;
        }
        if(request.getDescription() != null){
            announcement.setDescription(request.getDescription());
            changed = true;
        }

        return changed;
    }

    private boolean deleteImages(List<String> imagesToRemove, AnnouncementEntity announcement) {
        boolean changed = false;
        if (imagesToRemove != null && !imagesToRemove.isEmpty()) {
            for (String fileUrl : imagesToRemove) {
                Optional<ImageEntity> imageOptional = announcement.getImages().stream()
                        .filter(image -> image.getUrl().equals(fileUrl))
                        .findFirst();
                if (imageOptional.isPresent()) {
                    changed = true;
                    ImageEntity image = imageOptional.get();
                    announcement.getImages().remove(image);
                    String fileKey = fileUrl.replace(s3BucketLink + "/", "");
                    amazonFileService.deleteFile(fileKey);
                }
            }
        }
        return changed;
    }

    private boolean addImages(List<MultipartFile> imagesToAdd, AnnouncementEntity announcement) {
        if (imagesToAdd != null && !imagesToAdd.isEmpty()) {
            for (MultipartFile file : imagesToAdd) {
                ImageEntity image = amazonFileService.uploadFile(file, "announcements/");
                announcement.getImages().add(image);
            }
            return true;
        }
        return false;
    }
}
