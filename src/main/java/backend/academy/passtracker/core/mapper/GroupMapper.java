package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.rest.model.GroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = FacultyMapper.class)
public interface GroupMapper {

    GroupDTO entityToDTO(Group group);

}
