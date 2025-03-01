package backend.academy.passtracker.core.exception;

public class FileWithSuchNameAlreadyExistException extends RuntimeException {
    public FileWithSuchNameAlreadyExistException(String name) {
        super(String.format("Файл с названием: '%s' уже существует", name));
    }
}
