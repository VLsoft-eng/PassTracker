package backend.academy.passtracker.core.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("У вас нет прав доступа");
    }
    public ForbiddenException(String message) {super(message);}
}
