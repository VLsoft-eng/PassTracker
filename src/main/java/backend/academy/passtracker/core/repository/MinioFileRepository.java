package backend.academy.passtracker.core.repository;

import backend.academy.passtracker.core.entity.MinioFile;
import backend.academy.passtracker.core.entity.PassRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MinioFileRepository extends JpaRepository<MinioFile, UUID> {
}
