package backend.academy.passtracker.rest.model.auth;

import backend.academy.passtracker.core.enumeration.UserRole;

import java.util.List;

public record RegistrationRequest(
        String fullName,
        String email,
        Long groupNumber,
        String password
) {
}