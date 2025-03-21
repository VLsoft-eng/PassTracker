package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.service.AuthService;
import backend.academy.passtracker.rest.model.auth.LoginRequest;
import backend.academy.passtracker.rest.model.auth.LoginResponse;
import backend.academy.passtracker.rest.model.auth.RegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Авторизация", description = "Контроллер, отвечающий за авторизацию")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Регистрация",
            description = "Позволяет пользователю зарегистрироваться в системе"
    )
    @PostMapping("/registration")
    private LoginResponse registration(
            @RequestBody @Valid RegistrationRequest registrationRequest
    ) {
        return authService.register(registrationRequest);
    }

    @Operation(
            summary = "Вход в учетную запись",
            description = "Позволяет пользователю войти в учетную запись"
    )
    @PostMapping("/login")
    private LoginResponse login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        return authService.login(loginRequest);
    }

    @Operation(
            summary = "Выход из учетной записи",
            description = "Позволяет пользователю выйти из учетной записи"
    )
    @PostMapping("/logout")
    private void logout() {
        authService.logout();
    }
}
