package backend.academy.passtracker.core.exception;

import java.util.UUID;

public class FacultyNotFoundException extends RuntimeException {

    public FacultyNotFoundException(UUID id) {
        super(String.format("Факультет с идентификатором: %s не найден", id));
    }
}
