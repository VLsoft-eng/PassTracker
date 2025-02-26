package backend.academy.passtracker.rest.model.faculty;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FacultyDTO {

    private UUID id;

    private String facultyName;

}
