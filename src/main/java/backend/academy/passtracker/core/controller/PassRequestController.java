package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.config.security.userDetails.CustomUserDetails;
import backend.academy.passtracker.core.dto.UpdateExtendPassRequestDTO;
import backend.academy.passtracker.core.dto.UpdatePassRequestDTO;
import backend.academy.passtracker.core.service.PassRequestService;
import backend.academy.passtracker.rest.model.pass.request.*;
import io.minio.errors.MinioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/pass/request")
@RequiredArgsConstructor
@Tag(name = "Заявки на пропуск", description = "Контроллер, отвечающий за заявки на пропуск")
public class PassRequestController {

    private final PassRequestService passRequestService;

    @Operation(
            summary = "Получение страницы заявок на пропуск по параметрам (деканат, преподаватель)",
            description = "Позволяет получить страницу заявок на пропуск по параметрам"
    )
    @GetMapping("/pageable")
    private Page<ShortPassRequestDTO> getPassRequests(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String userSearchString,
            @RequestParam(required = false) Instant dateStart,
            @RequestParam(required = false) Instant dateEnd,
            @RequestParam(required = false) Instant date,
            @RequestParam(required = false) List<Long> groupNumber,
            @RequestParam(required = false) Boolean isAccepted,
            @PageableDefault(size = 10, page = 0, sort = "createTimestamp", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return passRequestService.getPassRequestsPage(
                userId,
                userSearchString,
                dateStart,
                dateEnd,
                date,
                groupNumber,
                isAccepted,
                pageable
        );
    }

    @Operation(
            summary = "Получение страницы СВОИХ заявок на пропуск (студент)",
            description = "Позволяет получить страницу СВОИХ заявок на пропуск"
    )
    @GetMapping("/my/pageable")
    private Page<ShortPassRequestDTO> getMyPassRequests(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Boolean isAccepted,
            @PageableDefault(size = 10, page = 0, sort = "createTimestamp", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return passRequestService.getMyPassRequests(
                customUserDetails.getId(),
                isAccepted,
                pageable
        );
    }

    @Operation(
            summary = "Получение заявки на пропуск по идентификатору",
            description = "Позволяет получить заявку на пропуск"
    )
    @GetMapping("/{passRequestId}")
    private PassRequestDTO getPassRequestById(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("passRequestId") UUID requestId
    ) {
        return passRequestService.getPassRequest(
                customUserDetails.getId(),
                requestId
        );
    }

    @Operation(
            summary = "Создание заявки на пропуск (студент)",
            description = "Позволяет создать заявку на пропуск"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private PassRequestDTO createPassRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Instant dateStart,
            @RequestParam Instant dateEnd,
            @RequestParam(required = false) String message,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ) throws MinioException {
        return passRequestService.createPassRequest(
                customUserDetails.getId(),
                dateStart,
                dateEnd,
                message,
                files
        );
    }

    @Operation(
            summary = "Изменение заявки на пропуск (студент)",
            description = "Позволяет студенту изменить заявку на пропуск," +
                    " ТОЛЬКО КОГДА ЗАЯВКА НЕ РАССМОТРЕНА ДЕКАНАТОМ"
    )
    @PatchMapping("/{passRequestId}")
    private PassRequestDTO updatePassRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("passRequestId") UUID passRequestId,
            @RequestBody UpdatePassRequestDTO updates
            ) {
        return passRequestService.updatePassRequest(customUserDetails.getId(), passRequestId, updates);
    }

    @Operation(
            summary = "Удаление заявки на пропуск (студент)",
            description = "Позволяет студенту удалить заявку на пропуск," +
                    " ТОЛЬКО КОГДА ЗАЯВКА НЕ РАССМОТРЕНА ДЕКАНАТОМ"
    )
    @DeleteMapping("/{passRequestId}")
    private void deletePassRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("passRequestId") UUID passRequestId
    ) {
        passRequestService.deletePassRequest(customUserDetails.getId(), passRequestId);
    }

    @Operation(
            summary = "Создание заявки на продление пропуска (студент)",
            description = "Позволяет создать заявку на продление пропуска"
    )
    @PostMapping(value = "/{requestId}/extend", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ExtendPassTimeRequestDTO createExtendPassTimeRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("requestId") UUID requestId,
            @RequestParam Instant dateEnd,
            @RequestParam(required = false) String message,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ) throws MinioException {
        return passRequestService.createExtendPassTimeRequest(
                customUserDetails.getId(),
                dateEnd,
                message,
                requestId,
                files
        );
    }

    @Operation(
            summary = "Изменение заявки на продление пропуска (студент)",
            description = "Позволяет студенту изменить заявку на продление пропуска," +
                    " ТОЛЬКО КОГДА ЗАЯВКА НЕ РАССМОТРЕНА ДЕКАНАТОМ"
    )
    @PatchMapping("/extend/{requestId}")
    private ExtendPassTimeRequestDTO updateExtendPassTimeRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("requestId") UUID requestId,
            @RequestParam UpdateExtendPassRequestDTO updates
    ) {
        return passRequestService.updateExtendPassTimeRequest(customUserDetails.getId(), requestId, updates);
    }

    @Operation(
            summary = "Удаление заявки на продление пропуска (студент)",
            description = "Позволяет студенту удалить заявку на продление пропуска," +
                    " ТОЛЬКО КОГДА ЗАЯВКА НЕ РАССМОТРЕНА ДЕКАНАТОМ"
    )
    @DeleteMapping("/extend/{requestId}")
    private void deleteExtendPassTimeRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("requestId") UUID requestId
    ) {
        passRequestService.deleteExtendPassTimeRequest(customUserDetails.getId(), requestId);
    }

}
