package backend.academy.passtracker.core.service;

import backend.academy.passtracker.rest.model.LoginRequest;
import backend.academy.passtracker.rest.model.LoginResponse;
import backend.academy.passtracker.rest.model.RegistrationRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse register(RegistrationRequest registrationRequest);
}
