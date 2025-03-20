package backend.academy.passtracker.rest.model.group;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateGroupRequest(
        @NotNull(message = "Номер группы не может быть пустым")
        @Positive(message = "Номер группы должен быть положительным числом")
        Long groupNumber
) {
}
