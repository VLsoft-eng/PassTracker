package backend.academy.passtracker.core.repository;

import backend.academy.passtracker.core.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllByIsDeleted(Boolean isDeleted);

}
