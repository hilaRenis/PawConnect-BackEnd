package nl.fontys.pawconnect.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAnnouncementException extends ResponseStatusException {
    public InvalidAnnouncementException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}