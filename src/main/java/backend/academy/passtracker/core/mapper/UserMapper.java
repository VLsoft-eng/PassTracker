package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.rest.model.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = GroupMapper.class)
public interface UserMapper {

    @Mapping(target = "isAccepted", constant = "false")
    @Mapping(target = "role", source = "userCreateDto.userRole")
    User createDTOtoEntity(UserCreateDto userCreateDto);

    UserDTO entityToDTO(User user);

}
