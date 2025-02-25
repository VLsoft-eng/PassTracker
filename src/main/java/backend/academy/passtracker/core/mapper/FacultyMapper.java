package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.entity.Faculty;
import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.rest.model.FacultyDTO;
import backend.academy.passtracker.rest.model.GroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    FacultyDTO entityToDTO(Faculty faculty);

}
