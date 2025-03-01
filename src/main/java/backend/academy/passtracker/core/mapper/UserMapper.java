package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.rest.model.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = GroupMapper.class)
public interface UserMapper {

    @Mapping(target = "role", source = "userCreateDto.userRole")
    @Mapping(target = "studentGroup", source = "userCreateDto.studentGroup",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
    )
    User createDTOtoEntity(UserCreateDto userCreateDto);

    @Mapping(target = "group", source = "user.studentGroup")
    UserDTO entityToDTO(User user);

}
