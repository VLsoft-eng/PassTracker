package backend.academy.passtracker.core.dto;

import backend.academy.passtracker.core.enumeration.UserRole;

public record UserCreateDto(String email, String fullName, String password, UserRole userRole) {
}
