package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "isAccepted", constant = "false")
    @Mapping(target = "role", source = "userCreateDto.userRole")
    User createDTOtoEntity(UserCreateDto userCreateDto);

}
