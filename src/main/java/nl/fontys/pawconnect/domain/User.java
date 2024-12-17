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
public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private UserRoles role;
}


