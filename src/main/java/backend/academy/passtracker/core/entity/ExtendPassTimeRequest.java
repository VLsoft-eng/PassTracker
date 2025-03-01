package backend.academy.passtracker.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@Entity(name = "extend_pass_time_request")
@AllArgsConstructor
@NoArgsConstructor
public class ExtendPassTimeRequest {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "pass_request_id", nullable = false)
    private UUID passRequestId;

    @Column(name = "date_end", nullable = false)
    private Instant dateEnd;

    @ManyToMany
    @JoinTable(
            name = "extend_pass_time_request_file",
            joinColumns = { @JoinColumn(name = "request_id") },
            inverseJoinColumns = { @JoinColumn(name = "file_id") }
    )
    private List<MinioFile> minioFiles;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @Column(name = "create_timestamp")
    private Instant createTimestamp;

    @Column(name = "update_timestamp")
    private Instant updateTimestamp;

    @Column(name = "message")
    private String message;

}
