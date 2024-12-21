package nl.fontys.pawconnect.business.interf.users;

import org.springframework.web.multipart.MultipartFile;

public interface ChangeUserAvatarUseCase {
    String changeUserAvatar(MultipartFile multipartFile, String userId);
}
