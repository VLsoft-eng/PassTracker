package backend.academy.passtracker.rest.model.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPatchDTO {

    private String email;

    private String fullName;

    private Long group;

}
