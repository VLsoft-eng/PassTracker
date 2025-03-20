package backend.academy.passtracker.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
@Entity(name = "minio_file")
@AllArgsConstructor
@NoArgsConstructor
public class MinioFile {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "upload_time", nullable = false)
    private Instant uploadTime;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "size", nullable = false)
    private Long size;

}
