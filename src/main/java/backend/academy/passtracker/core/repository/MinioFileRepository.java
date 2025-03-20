package backend.academy.passtracker.core.repository;

import backend.academy.passtracker.core.entity.MinioFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MinioFileRepository extends JpaRepository<MinioFile, UUID> {

    MinioFile findByName(String fileName);

    List<MinioFile> findAllByIdIn(List<UUID> ids);

}