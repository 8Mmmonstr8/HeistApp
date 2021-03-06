package ua.hubanov.heist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SkillException extends RuntimeException {

    public SkillException(String message) {
        super(message);
    }
}
