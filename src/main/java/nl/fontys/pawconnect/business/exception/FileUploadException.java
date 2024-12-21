package nl.fontys.pawconnect.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileUploadException extends ResponseStatusException {
    public FileUploadException(String errorCode) {
        super(HttpStatus.UNAUTHORIZED, errorCode);
    }
}