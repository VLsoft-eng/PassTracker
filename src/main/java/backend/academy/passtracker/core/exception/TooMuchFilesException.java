package backend.academy.passtracker.core.exception;

public class TooMuchFilesException extends RuntimeException {

    public TooMuchFilesException(String message) {
        super(message);
    }
}
