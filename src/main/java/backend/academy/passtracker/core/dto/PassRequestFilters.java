package backend.academy.passtracker.core.dto;

import backend.academy.passtracker.core.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class PassRequestFilters {
    private User user;
    private String userSearchString;
    private Instant createDateStart;
    private Instant createDateEnd;
    private Instant date;
    private Boolean isAccepted;
}
