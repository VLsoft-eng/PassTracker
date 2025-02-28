package backend.academy.passtracker.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private MinioFile minioFile;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @Column(name = "create_timestamp")
    private Instant createTimestamp;

    @Column(name = "update_timestamp")
    private Instant updateTimestamp;

    @Column(name = "message")
    private String message;

}
