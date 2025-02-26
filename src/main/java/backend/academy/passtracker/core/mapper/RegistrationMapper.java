package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.core.exception.BadRequestException;
import backend.academy.passtracker.rest.model.auth.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

    @Mapping(target = "isAccepted", expression = "java(processIsAccepted(request))")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "groups", source = "groups")
    UserCreateDto toUserCreateDto(RegistrationRequest request, String hashedPassword, List<Group> groups);

    default Boolean processIsAccepted(RegistrationRequest request) {
        return request.userRole().equals(UserRole.ROLE_STUDENT) ? true : null;
    }
}
