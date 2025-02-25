package backend.academy.passtracker.core.exception;

import java.util.UUID;

public class OperationNotFoundException extends RuntimeException {

    public OperationNotFoundException(UUID id) {
        super(String.format("Операция с идентификатором: %s не найдена", id));
    }
}
