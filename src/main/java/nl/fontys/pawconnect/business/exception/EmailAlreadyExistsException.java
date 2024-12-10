package nl.fontys.pawconnect.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyExistsException extends ResponseStatusException {
    public EmailAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "USERNAME_ALREADY_EXISTS");
    }

}
