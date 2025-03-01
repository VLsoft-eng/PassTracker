package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.config.security.userDetails.CustomUserDetails;
import backend.academy.passtracker.core.service.PassRequestService;
import backend.academy.passtracker.rest.model.pass.request.ExtendPassTimeRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.ExtendPassTimeRequestRequest;
import backend.academy.passtracker.rest.model.pass.request.PassRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.PassRequestRequest;
import io.minio.errors.MinioException;
import jakarta.validation.Valid;
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
public class PassRequestController {

    private final PassRequestService passRequestService;

    @GetMapping("/pageable")
    private Page<PassRequestDTO> getPassRequests(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String userSearchString,
            @RequestParam(required = false) Instant createDateStart,
            @RequestParam(required = false) Instant createDateEnd,
            @RequestParam(required = false) Instant date,
            @RequestParam(required = false) Long groupNumber,
            @RequestParam(required = false) Boolean isAccepted,
            @PageableDefault(size = 10, page = 0, sort = "createTimestamp", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return passRequestService.getPassRequests(
                userId,
                userSearchString,
                createDateStart,
                createDateEnd,
                date,
                groupNumber,
                isAccepted,
                pageable
        );
    }

    @GetMapping("/my/pageable")
    private Page<PassRequestDTO> getMyPassRequests(
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
                PassRequestRequest.builder().dateStart(dateStart).dateEnd(dateEnd).message(message).build(),
                files
        );
    }

    @PatchMapping("/{passRequestId}")
    private PassRequestDTO updatePassRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("passRequestId") UUID passRequestId,
            @RequestBody Map<String, Object> updates
    ) {
        return passRequestService.updatePassRequest(customUserDetails.getId(), passRequestId, updates);
    }

    @DeleteMapping("/{passRequestId}")
    private void deletePassRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("passRequestId") UUID passRequestId
    ) {
        passRequestService.deletePassRequest(customUserDetails.getId(), passRequestId);
    }

    @PostMapping("/extend")
    private ExtendPassTimeRequestDTO createExtendPassTimeRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam UUID requestId,
            @RequestParam Instant dateEnd,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ) throws MinioException {
        return passRequestService.createExtendPassTimeRequest(
                customUserDetails.getId(),
                ExtendPassTimeRequestRequest.builder().passRequestId(requestId).dateEnd(dateEnd).build(),
                files
        );
    }

    @PatchMapping("/extend/{requestId}")
    private ExtendPassTimeRequestDTO updateExtendPassTimeRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("requestId") UUID requestId,
            @RequestBody Map<String, Object> updates
    ) {
        return passRequestService.updateExtendPassTimeRequest(customUserDetails.getId(), requestId, updates);
    }

    @DeleteMapping("/extend/{requestId}")
    private void deleteExtendPassTimeRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("requestId") UUID requestId
    ) {
        passRequestService.deleteExtendPassTimeRequest(customUserDetails.getId(), requestId);
    }

}
