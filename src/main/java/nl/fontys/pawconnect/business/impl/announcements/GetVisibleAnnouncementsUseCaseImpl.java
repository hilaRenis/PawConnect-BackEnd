package nl.fontys.pawconnect.business.impl.announcements;

import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.exception.InvalidUserException;
import nl.fontys.pawconnect.business.exception.UnauthorizedAccessException;
import nl.fontys.pawconnect.business.impl.converter.AnnouncementConverter;
import nl.fontys.pawconnect.business.interf.announcements.GetVisibleAnnouncementsUseCase;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.domain.Announcement;
import nl.fontys.pawconnect.domain.requests.announcements.GetVisibleAnnouncementsRequest;
import nl.fontys.pawconnect.domain.responses.announcements.GetVisibleAnnouncementsResponse;
import nl.fontys.pawconnect.persistence.entity.UserEntity;
import nl.fontys.pawconnect.persistence.interf.AnnouncementRepository;
import nl.fontys.pawconnect.persistence.interf.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetVisibleAnnouncementsUseCaseImpl implements GetVisibleAnnouncementsUseCase {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final AccessToken accessToken;

    @Override
    @Transactional
    public GetVisibleAnnouncementsResponse getVisibleAnnouncements(GetVisibleAnnouncementsRequest request) {
        if(accessToken.getUserId().equals(request.getUserId())) {
            Optional<UserEntity> userOptional = userRepository.findById(request.getUserId());
            if (userOptional.isEmpty()) {
                throw new InvalidUserException("USER_ID_INVALID");
            }

            UserEntity user = userOptional.get();
            List<Announcement> announcements = announcementRepository.findAnnouncementsByUserRoleAndUserId(String.valueOf(user.getRole()), user.getId())
                    .stream().map(AnnouncementConverter::convert).toList();

            return GetVisibleAnnouncementsResponse.builder()
                    .announcements(announcements)
                    .build();
        } else {
            throw new UnauthorizedAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
        }

    }
}
