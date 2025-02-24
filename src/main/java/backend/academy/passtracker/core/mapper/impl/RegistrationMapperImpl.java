package backend.academy.passtracker.core.mapper.impl;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.core.mapper.RegistrationMapper;
import backend.academy.passtracker.rest.model.RegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapperImpl implements RegistrationMapper {
    public UserCreateDto toUserCreateDto(RegistrationRequest request, String hashedPassword, UserRole userRole) {
        return new UserCreateDto(
                request.email(),
                request.fullName(),
                request.password(),
                userRole);
    }
}
