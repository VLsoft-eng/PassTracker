package backend.academy.passtracker.core.exception;

public class BadExtensionException extends RuntimeException {
    public BadExtensionException(String extension) {
        super(String.format("Файл с расширением: %s не поддерживается", extension));
    }
}
