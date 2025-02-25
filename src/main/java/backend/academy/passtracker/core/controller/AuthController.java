package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.service.AuthService;
import backend.academy.passtracker.rest.model.LoginRequest;
import backend.academy.passtracker.rest.model.LoginResponse;
import backend.academy.passtracker.rest.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    private LoginResponse registration(
            @RequestBody RegistrationRequest registrationRequest
    ) {
        return authService.register(registrationRequest);
    }

    @PostMapping("/login")
    private LoginResponse login(
            @RequestBody LoginRequest loginRequest
    ) {
        return authService.login(loginRequest);
    }

}
