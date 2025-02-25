package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.rest.model.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

    @Mapping(target = "password", source = "hashedPassword")
    UserCreateDto toUserCreateDto(RegistrationRequest request, String hashedPassword);

}
