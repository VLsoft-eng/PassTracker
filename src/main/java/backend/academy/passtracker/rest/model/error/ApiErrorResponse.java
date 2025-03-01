package backend.academy.passtracker.rest.model.error;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> errorDetails,
        String path) {
}
