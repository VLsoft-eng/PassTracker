package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.rest.model.RegistrationRequest;

public interface RegistrationMapper {
    public UserCreateDto toUserCreateDto(RegistrationRequest request, String hashedPassword, UserRole userRole);
}
