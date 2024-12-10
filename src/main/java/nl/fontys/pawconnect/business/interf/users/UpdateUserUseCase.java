package nl.fontys.pawconnect.business.interf.users;

import nl.fontys.pawconnect.domain.requests.users.UpdateUserRequest;

public interface UpdateUserUseCase {
    void updateUser(UpdateUserRequest request);
}
