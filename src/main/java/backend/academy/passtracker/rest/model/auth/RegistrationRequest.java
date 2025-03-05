package backend.academy.passtracker.rest.model.auth;

import jakarta.validation.constraints.*;

public record RegistrationRequest(

        @NotBlank(message = "Полное имя не может быть пустым")
        @Size(max = 100, message = "Полное имя не может быть длиной более 100 символов")
        String fullName,

        @NotBlank(message = "Электронная почта не может быть пустой")
        @Email(message = "Адрес электронной почты должен быть в стандартном формате")
        @Size(max = 100, message = "Электронная почта не может быть длиной более 100 символов")
        String email,

        @NotNull(message = "Номер группы не может быть пустым")
        @Positive(message = "Номер группы должен быть положительным числом")
        Long groupNumber,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 8, max = 20, message = "Пароль должен быть от 8 до 20 символов")
        String password
) {
}