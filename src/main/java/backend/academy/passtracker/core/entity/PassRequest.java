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
@Entity(name = "pass_request")
@AllArgsConstructor
@NoArgsConstructor
public class PassRequest {

    @Id
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "date_start", nullable = false)
    private Instant dateStart;

    @Column(name = "date_end", nullable = false)
    private Instant dateEnd;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private MinioFile minioFile;

    @OneToMany(mappedBy = "passRequestId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExtendPassTimeRequest> extendPassTimeRequests;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @Column(name = "create_timestamp")
    private Instant createTimestamp;

    @Column(name = "update_timestamp")
    private Instant updateTimestamp;

    @Column(name = "message")
    private String message;

}
