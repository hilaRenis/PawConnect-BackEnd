package nl.fontys.pawconnect.domain.responses.users;

import lombok.Builder;
import lombok.Data;
import nl.fontys.pawconnect.persistence.entity.UserRoles;

@Data
@Builder
public class GetUserResponse {
    private String id;
    private String username;
    private String email;
    private String fullName;
    private UserRoles role;
}
