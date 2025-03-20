package backend.academy.passtracker.rest.model.pass.request;

import backend.academy.passtracker.rest.model.minio.file.ShortMinioFileDTO;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ShortExtendPassTimeRequestDTO {

    private UUID id;

    private Instant dateEnd;

    private List<ShortMinioFileDTO> minioFiles;

    private Boolean isAccepted;

    private Instant createTimestamp;

}
