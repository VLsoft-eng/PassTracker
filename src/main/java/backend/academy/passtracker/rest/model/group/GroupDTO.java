package backend.academy.passtracker.rest.model.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDTO {

    private Long groupNumber;

    private Boolean isDeleted;

}
