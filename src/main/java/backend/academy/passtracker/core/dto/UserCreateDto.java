package backend.academy.passtracker.core.dto;

import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.core.enumeration.UserRole;

import java.util.List;

public record UserCreateDto(
        String email,
        String fullName,
        String password,
        UserRole userRole,
        Boolean isBlocked,
        Group studentGroup
) {
}
