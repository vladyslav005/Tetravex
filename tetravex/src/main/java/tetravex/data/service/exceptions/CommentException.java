package tetravex.data.service.exceptions;

public class CommentException extends RuntimeException {
    public CommentException(String message) {
        super(message);
    }

    public CommentException(String message, Throwable cause) {
        super(message, cause);
    }
}
