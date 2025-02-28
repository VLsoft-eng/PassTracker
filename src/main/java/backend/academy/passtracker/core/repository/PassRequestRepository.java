package backend.academy.passtracker.core.repository;

import backend.academy.passtracker.core.entity.PassRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PassRequestRepository extends JpaRepository<PassRequest, UUID>, JpaSpecificationExecutor<PassRequest> {


}
