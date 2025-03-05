package backend.academy.passtracker.rest.model.pass.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PassRequestRequest {

    @NotNull(message = "Дата начала не может быть пустой")
    @FutureOrPresent(message = "Дата начала должна быть в будущем или настоящем")
    private Instant dateStart;

    @NotNull(message = "Дата окончания не может быть пустой")
    @Future(message = "Дата окончания должна быть в будущем")
    private Instant dateEnd;

    @NotBlank(message = "Сообщение не может быть пустым")
    @Size(max = 500, message = "Сообщение не может быть длиннее 500 символов")
    private String message;

    @AssertTrue(message = "Дата окончания должна быть позже даты начала")
    public boolean isDateEndAfterDateStart() {
        if (dateStart == null || dateEnd == null) {
            return true; // Если даты не заданы, валидация пропускается
        }
        return dateEnd.isAfter(dateStart);
    }
}