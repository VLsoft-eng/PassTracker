package backend.academy.passtracker.rest.model.user;

import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserDTO {

    private UUID id;

    private String email;

    private String fullName;

    private List<GroupDTO> groups;

    private UserRole role;

    private Boolean isAccepted;

}
