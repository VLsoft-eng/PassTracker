package backend.academy.passtracker.core.exception;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(UUID id) {
        super(String.format("Счёт с идентификатором: %s не найден", id));
    }
}
