package backend.academy.passtracker.rest.model.group;

import backend.academy.passtracker.rest.model.faculty.FacultyDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDTO {

    private Long groupNumber;

    private FacultyDTO faculty;

    private Boolean isDeleted;

}
