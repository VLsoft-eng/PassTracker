package backend.academy.passtracker.rest.model.pass.request;

import jakarta.validation.constraints.*;

import java.time.Instant;

public record PassRequestRequest(
        @NotNull(message = "Дата начала не может быть пустой")
        @FutureOrPresent(message = "Дата начала должна быть в будущем или настоящем")
        Instant dateStart,

        @NotNull(message = "Дата окончания не может быть пустой")
        @Future(message = "Дата окончания должна быть в будущем")
        Instant dateEnd,

        @Size(max = 500, message = "Сообщение не может быть длиннее 500 символов")
        String message
) {
    @AssertTrue(message = "Дата окончания должна быть позже даты начала")
    public boolean isDateEndAfterDateStart() {
        if (dateStart == null || dateEnd == null) {
            return true;
        }
        return dateEnd.isAfter(dateStart);
    }
}