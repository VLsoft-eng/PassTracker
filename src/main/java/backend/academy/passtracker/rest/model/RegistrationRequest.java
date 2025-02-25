package backend.academy.passtracker.rest.model;

import backend.academy.passtracker.core.enumeration.UserRole;

public record RegistrationRequest(String fullName, String email, String password, UserRole userRole) {
}