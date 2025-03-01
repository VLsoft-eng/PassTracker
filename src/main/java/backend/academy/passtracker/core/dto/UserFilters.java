package backend.academy.passtracker.core.dto;

import backend.academy.passtracker.core.enumeration.UserRole;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserFilters {
    private String email;
    private Long groupNumber;
    private String fullName;
    private UserRole role;
    private Boolean isBlocked;
}
