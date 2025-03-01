package backend.academy.passtracker.rest.model.pass.request;

import backend.academy.passtracker.core.entity.ExtendPassTimeRequest;
import backend.academy.passtracker.rest.model.minio.file.MinioFileDTO;
import backend.academy.passtracker.rest.model.user.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PassRequestDTO {

    private UUID id;

    private UserDTO user;

    private Instant dateStart;

    private Instant dateEnd;

    private List<MinioFileDTO> minioFiles;

    private List<ExtendPassTimeRequestDTO> extendPassTimeRequests;

    private Boolean isAccepted;

    private Instant createTimestamp;

    private Instant updateTimestamp;

    private String message;

}
