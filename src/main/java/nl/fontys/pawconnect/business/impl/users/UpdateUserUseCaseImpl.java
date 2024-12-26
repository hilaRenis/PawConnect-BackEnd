package nl.fontys.pawconnect.business.impl.users;

import lombok.RequiredArgsConstructor;
import nl.fontys.pawconnect.business.exception.*;
import nl.fontys.pawconnect.business.impl.validation.PasswordValidator;
import nl.fontys.pawconnect.business.interf.AmazonFileService;
import nl.fontys.pawconnect.business.interf.users.UpdateUserUseCase;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.domain.requests.users.UpdateUserRequest;
import nl.fontys.pawconnect.persistence.entity.ImageEntity;
import nl.fontys.pawconnect.persistence.entity.UserEntity;
import nl.fontys.pawconnect.persistence.interf.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final AccessToken accessToken;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;
    private final AmazonFileService amazonFileService;

    @Value("${aws.s3.bucket.link}")
    private String s3BucketLink;

    @Override
    @Transactional
    public void updateUser(String userId, UpdateUserRequest request, MultipartFile multipartFile) {
        if(accessToken.getUserId().equals(userId)) {
            Optional<UserEntity> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new InvalidUserException("USER_ID_INVALID");
            }

            UserEntity user = userOptional.get();
            boolean fieldsUpdated = updateFields(request, user);
            boolean avatarUpdated = updateAvatar(user, multipartFile);

            if (fieldsUpdated || avatarUpdated) {
                userRepository.save(user);
            }
        } else {
            throw new UnauthorizedAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
        }
    }

    private boolean updateFields(UpdateUserRequest request, UserEntity user){
        boolean changed = false;

        if(request.getUsername() != null){
            if(!userRepository.existsByUsername(request.getUsername())) {
                user.setUsername(request.getUsername());
                changed = true;
            }
            else {
                throw new UsernameAlreadyExistsException();
            }
        }
        if(request.getEmail() != null) {
            if(!userRepository.existsByEmail(request.getEmail())) {
                user.setEmail(request.getEmail());
                changed = true;
            }
            else {
                throw new EmailAlreadyExistsException();
            }
        }
        if(request.getFullName() != null){
            user.setFullName(request.getFullName());
            changed = true;
        }
        if(request.getPassword() != null){
            String errors = passwordValidator.validatePassword(request.getPassword());
            if(!errors.isEmpty()) {
                throw new InvalidPasswordException(errors);
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            changed = true;
        }

        return changed;
    }

    private boolean updateAvatar(UserEntity user, MultipartFile avatarFile) {
        if (avatarFile != null && !avatarFile.isEmpty()) {
            // Delete old avatar if it exists
            if (user.getAvatar() != null) {
                String oldFileKey = user.getAvatar().getUrl().replace(s3BucketLink + "/", "");
                amazonFileService.deleteFile(oldFileKey);
            }

            ImageEntity newAvatar = amazonFileService.uploadFile(avatarFile, "avatars/");
            user.setAvatar(newAvatar);
            return true;
        }
        return false;
    }
}
