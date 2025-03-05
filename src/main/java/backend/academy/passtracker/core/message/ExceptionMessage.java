package backend.academy.passtracker.core.message;

public interface ExceptionMessage {
    String EMAIL_ALREADY_USED = "Электронная почта уже используется";
    String REQUIRED_GROUP = "Для действия нужно быть в группе";
    String CHANGE_PROCESSED_REQUEST = "Нельзя изменить уже рассмотренный запрос";
    String DELETE_PROCESSED_REQUEST = "Нельзя удалить уже рассмотренный запрос";
    String START_AFTER_END_DATE = "Дата окончания не может быть раньше даты начала";
    String START_AFTER_END_DATE_EXTEND =
            "Дата окончания в запросе на продление не может быть раньше даты окончания в основном запросе";
}
