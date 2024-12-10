package nl.fontys.pawconnect.domain.requests.users;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    @NotNull
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String password;
}
