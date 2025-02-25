package backend.academy.passtracker.rest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDTO {

    private Long groupNumber;

    private FacultyDTO faculty;

}
