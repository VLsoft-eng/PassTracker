package backend.academy.passtracker.core.repository;

import backend.academy.passtracker.core.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FacultyRepository extends JpaRepository<Faculty, UUID> {

    @Query(
            value = "select * from faculty f " +
                    "where lower(f.faculty_name) like lower(concat('%', :facultyName, '%'))",
            nativeQuery = true
    )
    List<Faculty> findAllByFacultyNameLike(
            @Param("facultyName") String facultyName
    );

}
