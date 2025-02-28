package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.config.security.userDetails.CustomUserDetails;
import backend.academy.passtracker.core.service.PassRequestService;
import backend.academy.passtracker.rest.model.pass.request.ExtendPassTimeRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.ExtendPassTimeRequestRequest;
import backend.academy.passtracker.rest.model.pass.request.PassRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.PassRequestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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
                isAccepted,
                pageable
        );
    }

    @PostMapping
    private PassRequestDTO createPassRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody PassRequestRequest passRequestRequest
    ) {
        return passRequestService.createPassRequest(customUserDetails.getId(), passRequestRequest);
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
            @RequestBody ExtendPassTimeRequestRequest extendPassTimeRequestRequest
    ) {
        return passRequestService.createExtendPassTimeRequest(customUserDetails.getId(), extendPassTimeRequestRequest);
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
