package backend.academy.passtracker.core.entity;

import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.core.utils.UserRoleConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_number")
    )
    private List<Group> groups;

    @Column(name = "role")
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

}
