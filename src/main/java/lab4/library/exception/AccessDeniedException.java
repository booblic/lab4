package lab4.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class with the status code 403
 * @author Кирилл
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access Denied")
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
