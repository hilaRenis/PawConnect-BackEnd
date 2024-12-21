package nl.fontys.pawconnect.business.interf.users;

import nl.fontys.pawconnect.domain.requests.users.LoginUserRequest;

public interface LoginUserUseCase {
    String loginUser(LoginUserRequest request);
}
