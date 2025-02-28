package backend.academy.passtracker.rest.model.pass.request;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class ExtendPassTimeRequestRequest {

    private UUID passRequestId;

    private Instant dateEnd;

}
