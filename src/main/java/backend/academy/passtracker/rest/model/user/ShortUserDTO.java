package backend.academy.passtracker.rest.model.user;

import backend.academy.passtracker.core.enumeration.UserRole;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ShortUserDTO {

    private UUID id;

    private String fullName;

    private UserRole role;

}
