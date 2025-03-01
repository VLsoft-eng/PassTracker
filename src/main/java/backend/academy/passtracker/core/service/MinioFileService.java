package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.entity.MinioFile;
import io.minio.errors.MinioException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MinioFileService {

    MinioFile uploadFile(MultipartFile file, UUID userId) throws MinioException;

    ResponseEntity<InputStreamResource> downloadFile(UUID fileId, UUID requestId) throws MinioException;

}