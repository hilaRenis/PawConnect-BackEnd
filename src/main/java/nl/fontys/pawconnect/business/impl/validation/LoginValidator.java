package nl.fontys.pawconnect.business.impl.validation;

import lombok.RequiredArgsConstructor;
import nl.fontys.pawconnect.business.exception.InvalidUserException;
import nl.fontys.pawconnect.business.exception.UnauthorizedAccessException;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.persistence.interf.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginValidator {
    private final UserRepository userRepository;

    public void validateToken(AccessToken accessToken, String userId) {
        if(accessToken.getUserId().equals(userId)) {
            if (!userRepository.existsById(userId)) {
                throw new InvalidUserException("INVALID_USER_ID");
            }
        } else {
            throw new UnauthorizedAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
        }
    }
}
