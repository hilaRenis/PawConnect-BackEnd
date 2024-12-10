package nl.fontys.pawconnect.business.interf.users;


import nl.fontys.pawconnect.domain.requests.users.GetUserRequest;
import nl.fontys.pawconnect.domain.responses.users.GetUserResponse;

public interface GetUserUseCase {
    GetUserResponse getUser(GetUserRequest request);
}
