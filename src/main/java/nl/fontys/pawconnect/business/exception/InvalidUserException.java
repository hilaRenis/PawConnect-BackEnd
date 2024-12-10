package nl.fontys.pawconnect.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUserException extends ResponseStatusException {
    public InvalidUserException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}