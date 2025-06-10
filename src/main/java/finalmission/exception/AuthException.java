package finalmission.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {

    private final HttpStatus status = HttpStatus.UNAUTHORIZED;

    public AuthException(String message) {
        super(message);
    }
}
