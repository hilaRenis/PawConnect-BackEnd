package nl.fontys.pawconnect.domain.responses.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResponse {
    private String accessToken;
}
