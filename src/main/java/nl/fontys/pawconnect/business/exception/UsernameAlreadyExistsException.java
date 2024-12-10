package nl.fontys.pawconnect.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameAlreadyExistsException extends ResponseStatusException {
    public UsernameAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "USERNAME_ALREADY_EXISTS");
    }

}
