package nl.fontys.pawconnect.domain.requests.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {
    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;
}
