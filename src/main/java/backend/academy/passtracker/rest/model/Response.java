package backend.academy.passtracker.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ на запросы")
public class Response {
    @Schema(description = "Статус ответа", example = "200")
    private int status;

    @Schema(description = "Время ответа", example = "2024-06-18T07:07:35.122Z")
    private LocalDateTime timestamp;

    @Schema(description = "Сообщение ответа", example = "Ответ успешно создан")
    private String message;
}
