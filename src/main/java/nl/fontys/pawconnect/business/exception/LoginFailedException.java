package nl.fontys.pawconnect.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginFailedException extends ResponseStatusException {
    public LoginFailedException() {
        super(HttpStatus.UNAUTHORIZED, "LOGIN_FAILED");
    }
}