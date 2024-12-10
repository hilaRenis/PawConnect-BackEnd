package nl.fontys.pawconnect.business.interf.users;

import nl.fontys.pawconnect.domain.requests.users.LoginUserRequest;
import nl.fontys.pawconnect.domain.responses.users.LoginUserResponse;

public interface LoginUserUseCase {
    LoginUserResponse loginUser(LoginUserRequest request);
}
