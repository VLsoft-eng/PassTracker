package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.rest.model.auth.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

    @Mapping(target = "isBlocked", constant = "false")
    @Mapping(target = "userRole", constant = "ROLE_STUDENT")
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "studentGroup", source = "studentGroup",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
    )
    UserCreateDto toUserCreateDto(RegistrationRequest request, String hashedPassword, Group studentGroup);
}
