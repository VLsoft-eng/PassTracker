package backend.academy.passtracker.rest.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Электронная почта не может быть пустой")
        @Email(message = "Адрес электронной почты должен быть в стандартном формате")
        @Size(max = 100, message = "Электронная почта не может быть длиной более 100 символов")
        String email,
        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 8, max = 20, message = "Пароль должен быть от 8 до 20 символов")
        String password
) {
}
