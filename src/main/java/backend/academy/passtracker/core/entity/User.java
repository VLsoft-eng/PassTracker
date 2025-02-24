package backend.academy.passtracker.core.entity;

import backend.academy.passtracker.core.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
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

    @Column(name = "role")
    private UserRole role;
}
