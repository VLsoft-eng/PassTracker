package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.entity.ExtendPassTimeRequest;
import backend.academy.passtracker.core.entity.PassRequest;
import backend.academy.passtracker.rest.model.pass.request.ExtendPassTimeRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.ExtendPassTimeRequestRequest;
import backend.academy.passtracker.rest.model.pass.request.PassRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.PassRequestRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public interface PassRequestService {
    PassRequestDTO getPassRequest(UUID requestId);

    PassRequest getRawPassRequest(UUID requestId);

    @Transactional(readOnly = true)
    ExtendPassTimeRequestDTO getExtendPassTimeRequest(UUID requestId);

    @Transactional(readOnly = true)
    ExtendPassTimeRequest getRawExtendPassTimeRequest(UUID requestId);

    @Transactional(readOnly = true)
    Page<PassRequestDTO> getPassRequests(
            UUID userId,
            String userSearchString,
            Instant createDateStart,
            Instant createDateEnd,
            Instant date,
            Boolean isAccepted,
            Pageable pageable
    );

    PassRequestDTO createPassRequest(UUID userId, PassRequestRequest request);

    PassRequestDTO updatePassRequest(UUID userId, UUID passRequestId, Map<String, Object> updates);

    void deletePassRequest(UUID userId, UUID passRequestId);

    ExtendPassTimeRequestDTO createExtendPassTimeRequest(UUID userId, ExtendPassTimeRequestRequest request);

    ExtendPassTimeRequestDTO updateExtendPassTimeRequest(
            UUID userId,
            UUID requestId,
            Map<String, Object> updates
    );

    void deleteExtendPassTimeRequest(UUID userId, UUID extendPassRequestId);
}
