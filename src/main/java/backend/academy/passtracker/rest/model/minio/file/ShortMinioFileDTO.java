package backend.academy.passtracker.rest.model.minio.file;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ShortMinioFileDTO {

    private UUID id;

    private String name;

}
