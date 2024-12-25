package nl.fontys.pawconnect.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.pawconnect.persistence.entity.UserRoles;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String fullName;
    private UserRoles role;
    private Image avatar;
}


