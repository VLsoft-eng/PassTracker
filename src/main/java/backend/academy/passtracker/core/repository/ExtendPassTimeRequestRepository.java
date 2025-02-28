package backend.academy.passtracker.core.repository;

import backend.academy.passtracker.core.entity.ExtendPassTimeRequest;
import backend.academy.passtracker.core.entity.PassRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExtendPassTimeRequestRepository extends JpaRepository<ExtendPassTimeRequest, UUID> {

    void deleteAllByPassRequestId(UUID passRequestId);

}
