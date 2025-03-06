package backend.academy.passtracker.rest.model.pass.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;


public record ExtendPassTimeRequestRequest(
        @NotNull(message = "ID запроса пропуска не может быть пустым")
        UUID passRequestId,

        @NotNull(message = "Дата окончания не может быть пустой")
        @Future(message = "Дата окончания должна быть в будущем")
        Instant dateEnd
) {
}
