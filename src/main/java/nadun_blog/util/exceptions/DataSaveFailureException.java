package nadun_blog.util.exceptions;

public class DataSaveFailureException extends Exception {
    public DataSaveFailureException() {
        super("Failed to save data");
    }
}
