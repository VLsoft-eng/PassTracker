package backend.academy.passtracker.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@Entity(name = "faculty")
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "faculty_name")
    private String facultyName;

}
