package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupDTO entityToDTO(Group group);

}
