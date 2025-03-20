package backend.academy.passtracker.core.service;

import backend.academy.passtracker.rest.model.auth.LoginRequest;
import backend.academy.passtracker.rest.model.auth.LoginResponse;
import backend.academy.passtracker.rest.model.auth.RegistrationRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    LoginResponse register(RegistrationRequest registrationRequest);

    void logout();
}
