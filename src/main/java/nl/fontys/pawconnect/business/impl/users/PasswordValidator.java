package nl.fontys.pawconnect.business.impl.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordValidator {
    public String validatePassword(String password) {
        String errors = "";
        if (!password.matches(".*[a-z].*")) {
            errors = errors + "Password must include at least one lowercase letter.";
        }
        if (!password.matches(".*[A-Z].*")) {
            errors = errors + "Password must include at least one uppercase letter.";
        }
        if (!password.matches(".*\\W.*")) {
            errors = errors + "Password must include at least one special character.";
        }
        if (password.length() < 8) {
            errors = errors + "Password must be at least 8 characters long.";
        }
        return errors;
    }
}
