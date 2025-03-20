package backend.academy.passtracker.core.exception;


import java.util.UUID;

public class PassRequestNotFoundException extends RuntimeException {

    public PassRequestNotFoundException(UUID id) {
        super(String.format("Заявка с идентификатором: %s не найдена", id));
    }
}
