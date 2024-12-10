package nl.fontys.pawconnect.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedAccessException extends ResponseStatusException {
    public UnauthorizedAccessException(String errorCode) {
        super(HttpStatus.UNAUTHORIZED, errorCode);
    }
}