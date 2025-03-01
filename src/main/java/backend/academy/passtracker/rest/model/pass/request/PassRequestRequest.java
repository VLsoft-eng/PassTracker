package backend.academy.passtracker.rest.model.pass.request;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class PassRequestRequest {

    private Instant dateStart;

    private Instant dateEnd;

    private String message;

}
