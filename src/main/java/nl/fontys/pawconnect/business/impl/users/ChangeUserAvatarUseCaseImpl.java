package nl.fontys.pawconnect.business.impl.users;

import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.exception.InvalidUserException;
import nl.fontys.pawconnect.business.exception.UnauthorizedAccessException;
import nl.fontys.pawconnect.business.interf.AmazonFileService;
import nl.fontys.pawconnect.business.interf.users.ChangeUserAvatarUseCase;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.persistence.entity.UserEntity;
import nl.fontys.pawconnect.persistence.interf.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChangeUserAvatarUseCaseImpl implements ChangeUserAvatarUseCase {
    private UserRepository userRepository;
    private AccessToken accessToken;
    private AmazonFileService amazonFileService;

    @Override
    @Transactional
    public String changeUserAvatar(MultipartFile multipartFile, String userId) {
        if(accessToken.getUserId().equals(userId)) {
            Optional<UserEntity> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new InvalidUserException("USER_ID_INVALID");
            }

            UserEntity user = userOptional.get();
            String amazonFilePath = amazonFileService.uploadFile(multipartFile, "avatars/" + user.getUsername());

            user.setAvatarUrl(amazonFilePath);
            userRepository.save(user);
            return amazonFilePath;

        } else {
            throw new UnauthorizedAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
        }
    }
}
