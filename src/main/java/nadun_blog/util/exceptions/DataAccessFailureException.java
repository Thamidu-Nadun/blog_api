package nadun_blog.util.exceptions;

public class DataAccessFailureException extends Exception {
    public DataAccessFailureException() {
        super("Failed to access data");
    }

    public DataAccessFailureException(String message) {
        super(message);
    }
}