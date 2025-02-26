package backend.academy.passtracker.core.exception;


import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID id) {
        super(String.format("Пользователь с идентификатором: %s не найден", id));
    }
}
