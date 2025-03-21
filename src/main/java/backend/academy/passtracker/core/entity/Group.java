package backend.academy.passtracker.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@Entity(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @Column(name = "group_number")
    private Long groupNumber;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
