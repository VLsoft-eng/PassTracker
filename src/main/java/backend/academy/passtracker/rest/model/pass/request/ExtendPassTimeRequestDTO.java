package backend.academy.passtracker.rest.model.pass.request;

import backend.academy.passtracker.rest.model.minio.file.MinioFileDTO;
import backend.academy.passtracker.rest.model.user.UserDTO;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class ExtendPassTimeRequestDTO {

    private UUID id;

    private UUID passRequestId;

    private Instant dateEnd;

    private MinioFileDTO minioFile;

    private Boolean isAccepted;

    private Instant createTimestamp;

    private Instant updateTimestamp;

    private String message;

}
