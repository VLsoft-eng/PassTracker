package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.service.MinioFileService;
import io.minio.errors.MinioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Tag(name = "Файлы", description = "Контроллер, отвечающий за прикрепленные файлы")
public class MinioFileController {

    private final MinioFileService minioFileService;

    @Operation(
            summary = "Скачивание прикрепленного файла к заявке",
            description = "Позволяет пользователю скачать прикрепленный файл"
    )
    @GetMapping("/download")
    private ResponseEntity<InputStreamResource> downloadFile(
            @RequestParam UUID requestId,
            @RequestParam UUID fileId
    ) throws MinioException {
        return minioFileService.downloadFile(fileId, requestId);
    }

}
