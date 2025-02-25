package backend.academy.passtracker.core.exception;


public class GroupNotFoundException extends RuntimeException {

    public GroupNotFoundException(Long id) {
        super(String.format("Группа с идентификатором: %s не найдена", id));
    }
}
