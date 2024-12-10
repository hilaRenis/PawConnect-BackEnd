package nl.fontys.pawconnect.business.interf.users;

import nl.fontys.pawconnect.domain.requests.users.CreateUserRequest;
import nl.fontys.pawconnect.domain.responses.users.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}
