package backend.academy.passtracker.rest.model.pass.request;

import backend.academy.passtracker.rest.model.minio.file.ShortMinioFileDTO;
import backend.academy.passtracker.rest.model.user.ShortUserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ShortPassRequestDTO {

    private UUID id;

    private ShortUserDTO user;

    private Instant dateStart;

    private Instant dateEnd;

    private List<ShortMinioFileDTO> minioFiles;

    private List<ShortExtendPassTimeRequestDTO> extendPassTimeRequests;

    private Boolean isAccepted;

    private Instant createTimestamp;

}
