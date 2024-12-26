package nl.fontys.pawconnect.business.interf.users;

import nl.fontys.pawconnect.domain.requests.users.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateUserUseCase {
    void updateUser(String userId, UpdateUserRequest request, MultipartFile multipartFile);
}
