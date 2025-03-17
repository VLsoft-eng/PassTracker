package backend.academy.passtracker.core.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class UpdateExtendPassRequestDTO {

    private Instant dateEnd;
    private String message;

}
