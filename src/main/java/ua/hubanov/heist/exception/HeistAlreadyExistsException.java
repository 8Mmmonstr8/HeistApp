package ua.hubanov.heist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HeistAlreadyExistsException extends RuntimeException {

    public HeistAlreadyExistsException(String message) {
        super(message);
    }
}
