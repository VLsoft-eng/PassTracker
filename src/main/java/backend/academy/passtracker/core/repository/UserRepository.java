package backend.academy.passtracker.core.repository;

import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.enumeration.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(
            value = "select * from users u " +
                    "where lower(u.full_name) like lower(concat('%', :fullName, '%')) " +
                    "and lower(u.email) like lower(concat('%', :email, '%')) " +
                    "and u.is_accepted = :isAccepted",
            nativeQuery = true
    )
    Page<User> findAllUsersBySearchStrings(
            @Param("fullName") String fullName,
            @Param("email") String email,
            @Param("isAccepted") Boolean isAccepted,
            Pageable pageable
    );
}
