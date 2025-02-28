package backend.academy.passtracker.rest.model.minio.file;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class MinioFileDTO {

    private UUID id;

    private Instant uploadTime;

    private String name;

    private Long size;

}
