package backend.academy.passtracker.core.exception;


import java.util.UUID;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(UUID id) {
        super(String.format("Файл с идентификатором: %s не найден", id));
    }
}
