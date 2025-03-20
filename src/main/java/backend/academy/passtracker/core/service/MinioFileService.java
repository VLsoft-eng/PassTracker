package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.entity.MinioFile;
import backend.academy.passtracker.rest.model.minio.file.MinioFileDTO;
import io.minio.errors.MinioException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MinioFileService {

    MinioFile uploadFile(MultipartFile file, UUID userId) throws MinioException;

    ResponseEntity<InputStreamResource> downloadFile(UUID fileId, UUID requestId) throws MinioException;

    MinioFileDTO getMinioFile(UUID fileId);
}