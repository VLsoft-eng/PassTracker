package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.entity.MinioFile;
import backend.academy.passtracker.core.exception.BadExtensionException;
import backend.academy.passtracker.core.exception.FileNotFoundException;
import backend.academy.passtracker.core.exception.PassRequestNotFoundException;
import backend.academy.passtracker.core.mapper.MinioFileMapper;
import backend.academy.passtracker.core.repository.ExtendPassTimeRequestRepository;
import backend.academy.passtracker.core.repository.MinioFileRepository;
import backend.academy.passtracker.core.repository.PassRequestRepository;
import backend.academy.passtracker.core.service.MinioFileService;
import io.minio.MinioClient;
import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.BucketExistsArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioFileServiceImpl implements MinioFileService {
    private final MinioFileRepository minioFileRepository;
    private final PassRequestRepository passRequestRepository;
    private final ExtendPassTimeRequestRepository extendPassTimeRequestRepository;
    private final MinioClient minioClient;
    private final MinioFileMapper minioFileMapper;

    @Value("${file-upload.allowed-extensions}")
    private List<String> allowedExtensions;

    @Transactional
    @Override
    public MinioFile uploadFile(MultipartFile file, UUID userId) throws MinioException {
        if (file.getSize() <= 0) {
            return null;
        }

        String filename = file.getOriginalFilename();
        int dotIndex = filename.lastIndexOf('.');
        String extension = filename.substring(dotIndex + 1);

        if (!allowedExtensions.contains(extension)) {
            throw new BadExtensionException(extension);
        }

        try {
            String bucketName = "user-" + userId;
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            String timestamp = String.valueOf(System.currentTimeMillis());
            filename = filename.substring(0, dotIndex) + "_" + timestamp + "." + extension;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            MinioFile minioFile = new MinioFile();
            minioFile.setId(UUID.randomUUID());
            minioFile.setName(filename);
            minioFile.setUploadTime(Instant.now());
            minioFile.setSize(file.getSize());

            minioFileRepository.save(minioFile);
            return minioFile;
        } catch (MinioException e) {
            throw new MinioException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<InputStreamResource> downloadFile(UUID fileId, UUID passRequestId) throws MinioException {
        try {

            var passRequest = passRequestRepository.findById(passRequestId).orElse(null);
            var extendPassTimeRequest = extendPassTimeRequestRepository.findById(passRequestId).orElse(null);

            if (passRequest == null && extendPassTimeRequest == null) {
                throw new PassRequestNotFoundException(passRequestId);
            }

            UUID userId;

            if (extendPassTimeRequest != null) {
                passRequest = passRequestRepository.findById(extendPassTimeRequest.getPassRequestId())
                        .orElseThrow(() -> new PassRequestNotFoundException(passRequestId));
            }
            userId = passRequest.getUser().getId();

            MinioFile minioFile = minioFileRepository.findById(fileId)
                    .orElseThrow(() -> new FileNotFoundException(fileId));

            String filename = minioFile.getName();
            String bucketName = "user-" + userId;

            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= " + filename);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(stream));
        } catch (MinioException e) {
            throw new MinioException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
