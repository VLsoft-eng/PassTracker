package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.dto.UpdateExtendPassRequestDTO;
import backend.academy.passtracker.core.dto.UpdatePassRequestDTO;
import backend.academy.passtracker.core.entity.ExtendPassTimeRequest;
import backend.academy.passtracker.core.entity.PassRequest;
import backend.academy.passtracker.rest.model.pass.request.*;
import io.minio.errors.MinioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PassRequestService {
    PassRequestDTO getPassRequest(UUID userId, UUID requestId);

    PassRequest getRawPassRequest(UUID requestId);

    ExtendPassTimeRequest getRawExtendPassTimeRequest(UUID requestId);

    Page<ShortPassRequestDTO> getPassRequestsPage(
            UUID userId,
            String userSearchString,
            Instant dateStart,
            Instant dateEnd,
            Instant date,
            List<Long> groupNumber,
            Boolean isAccepted,
            Pageable pageable
    );

    List<ShortPassRequestDTO> getPassRequests(
            UUID userId,
            String userSearchString,
            Instant dateStart,
            Instant dateEnd,
            Instant date,
            List<Long> groupNumbers,
            Boolean isAccepted
    );

    Page<ShortPassRequestDTO> getMyPassRequests(
            UUID userId,
            Boolean isAccepted,
            Pageable pageable
    );

    PassRequestDTO createPassRequest(
            UUID userId,
            Instant dateStart,
            Instant dateEnd,
            String message,
            List<MultipartFile> files
    )
            throws MinioException;

    PassRequestDTO updatePassRequest(UUID userId, UUID passRequestId, UpdatePassRequestDTO updates);

    void deletePassRequest(UUID userId, UUID passRequestId);

    ExtendPassTimeRequestDTO createExtendPassTimeRequest(
            UUID userId,
            Instant dateEnd,
            String message,
            UUID passRequestId,
            List<MultipartFile> cv
    ) throws MinioException;

    ExtendPassTimeRequestDTO updateExtendPassTimeRequest(
            UUID userId,
            UUID requestId,
            UpdateExtendPassRequestDTO updates
    );

    void deleteExtendPassTimeRequest(UUID userId, UUID extendPassRequestId);

    PassRequestDTO processPassRequest(UUID requestId, Boolean isAccepted);

    PassRequestDTO processExtendPassTimeRequest(UUID requestId, Boolean isAccepted);

}
