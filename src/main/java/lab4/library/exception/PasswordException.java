package lab4.library.exception;

/**
 * Exception class incorrect password
 * @author Кирилл
 * @version 1.0
 */
public class PasswordException extends RuntimeException {

    public PasswordException() {
    }

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
