package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.dto.PassRequestFilters;
import backend.academy.passtracker.core.entity.ExtendPassTimeRequest;
import backend.academy.passtracker.core.entity.PassRequest;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.exception.BadRequestException;
import backend.academy.passtracker.core.exception.ForbiddenException;
import backend.academy.passtracker.core.exception.PassRequestNotFoundException;
import backend.academy.passtracker.core.mapper.ExtendPassTimeRequestMapper;
import backend.academy.passtracker.core.mapper.PassRequestMapper;
import backend.academy.passtracker.core.repository.ExtendPassTimeRequestRepository;
import backend.academy.passtracker.core.repository.PassRequestRepository;
import backend.academy.passtracker.core.service.PassRequestService;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.core.specification.PassRequestSpecification;
import backend.academy.passtracker.rest.model.pass.request.ExtendPassTimeRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.ExtendPassTimeRequestRequest;
import backend.academy.passtracker.rest.model.pass.request.PassRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.PassRequestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassRequestServiceImpl implements PassRequestService {

    private final UserService userService;

    private final PassRequestRepository passRequestRepository;
    private final ExtendPassTimeRequestRepository extendPassTimeRequestRepository;

    private final PassRequestMapper passRequestMapper;
    private final ExtendPassTimeRequestMapper extendPassTimeRequestMapper;

    @Transactional(readOnly = true)
    @Override
    public PassRequestDTO getPassRequest(UUID requestId) {
        return passRequestMapper.entityToDTO(
                getRawPassRequest(requestId)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PassRequest getRawPassRequest(UUID requestId) {
        return passRequestRepository.findById(requestId)
                .orElseThrow(() -> new PassRequestNotFoundException(requestId));
    }

    @Transactional(readOnly = true)
    @Override
    public ExtendPassTimeRequestDTO getExtendPassTimeRequest(UUID requestId) {
        return extendPassTimeRequestMapper.entityToDTO(
                getRawExtendPassTimeRequest(requestId)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ExtendPassTimeRequest getRawExtendPassTimeRequest(UUID requestId) {
        return extendPassTimeRequestRepository.findById(requestId)
                .orElseThrow(() -> new PassRequestNotFoundException(requestId));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PassRequestDTO> getPassRequests(
            UUID userId,
            String userSearchString,
            Instant createDateStart,
            Instant createDateEnd,
            Instant date,
            Long groupNumber,
            Boolean isAccepted,
            Pageable pageable
    ) {

        User user = null;
        if (userId != null) {
            user = userService.getRawUser(userId);
        }

        PassRequestFilters request = PassRequestFilters.builder()
                .user(user)
                .userSearchString(userSearchString)
                .createDateStart(createDateStart)
                .createDateEnd(createDateEnd)
                .date(date)
                .groupNumber(groupNumber)
                .isAccepted(isAccepted)
                .build();

        Specification<PassRequest> spec = getSpecByFilters(request);

        return passRequestRepository.findAll(spec, pageable).map(passRequestMapper::entityToDTO);
    }

    @Override
    public Page<PassRequestDTO> getMyPassRequests(UUID userId, Boolean isAccepted, Pageable pageable) {
        var user = userService.getRawUser(userId);

        PassRequestFilters request = PassRequestFilters.builder()
                .user(user)
                .isAccepted(isAccepted)
                .build();

        Specification<PassRequest> spec = getSpecByFilters(request);

        return passRequestRepository.findAll(spec, pageable).map(passRequestMapper::entityToDTO);
    }

    @Transactional
    @Override
    public PassRequestDTO createPassRequest(UUID userId, PassRequestRequest request) {
        var user = userService.getRawUser(userId);
//        MinioFile minioFile = minioFileRepository.findById(request.getMinioFileId())
//                .orElse(null);

        PassRequest passRequest = PassRequest.builder()
                .id(UUID.randomUUID())
                .user(user)
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .minioFile(null)
                .isAccepted(false)
                .createTimestamp(Instant.now())
                .build();

        passRequest = passRequestRepository.save(passRequest);
        return passRequestMapper.entityToDTO(passRequest);
    }

    @Transactional
    @Override
    public PassRequestDTO updatePassRequest(UUID userId, UUID passRequestId, Map<String, Object> updates) {
        PassRequest passRequest = passRequestRepository.findById(passRequestId)
                .orElseThrow(() -> new PassRequestNotFoundException(passRequestId));

        if (!passRequest.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }

        if (passRequest.getIsAccepted() != null) {
            throw new BadRequestException("Нельзя изменить уже рассмотренный запрос");
        }

        PassRequest finalPassRequest = passRequest;
        updates.forEach((key, value) -> {
            switch (key) {
                case "dateStart" ->
                        finalPassRequest.setDateStart(Instant.ofEpochSecond(Long.parseLong(value.toString())));
                case "dateEnd" -> finalPassRequest.setDateEnd(Instant.ofEpochSecond(Long.parseLong(value.toString())));
                case "minioFile" -> finalPassRequest.setMinioFile(null);
            }
        });

        passRequest = passRequestRepository.save(finalPassRequest);
        return passRequestMapper.entityToDTO(passRequest);
    }

    @Transactional
    @Override
    public void deletePassRequest(UUID userId, UUID passRequestId) {
        PassRequest passRequest = passRequestRepository.findById(passRequestId)
                .orElseThrow(() -> new PassRequestNotFoundException(passRequestId));

        if (!passRequest.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }

        if (passRequest.getIsAccepted() != null) {
            throw new BadRequestException("Нельзя удалить уже рассмотренный запрос");
        }

        passRequestRepository.delete(passRequest);
    }

    @Transactional
    @Override
    public ExtendPassTimeRequestDTO createExtendPassTimeRequest(UUID userId, ExtendPassTimeRequestRequest request) {
        var passRequest = getRawPassRequest(request.getPassRequestId());

        if (!passRequest.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }

        ExtendPassTimeRequest extendRequest = ExtendPassTimeRequest.builder()
                .id(UUID.randomUUID())
                .passRequestId(request.getPassRequestId())
                .dateEnd(request.getDateEnd())
                .isAccepted(false)
                .createTimestamp(Instant.now())
                .build();

        extendRequest = extendPassTimeRequestRepository.save(extendRequest);
        return extendPassTimeRequestMapper.entityToDTO(extendRequest);
    }

    @Transactional
    @Override
    public ExtendPassTimeRequestDTO updateExtendPassTimeRequest(
            UUID userId,
            UUID requestId,
            Map<String, Object> updates
    ) {
        ExtendPassTimeRequest extendRequest = extendPassTimeRequestRepository.findById(requestId)
                .orElseThrow(() -> new PassRequestNotFoundException(requestId));

        var passRequest = getRawPassRequest(extendRequest.getPassRequestId());

        if (!passRequest.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }

        if (extendRequest.getIsAccepted() != null) {
            throw new BadRequestException("Нельзя изменить уже рассмотренный запрос");
        }

        ExtendPassTimeRequest finalExtendRequest = extendRequest;
        updates.forEach((key, value) -> {
            if ("dateEnd".equals(key)) {
                finalExtendRequest.setDateEnd(Instant.ofEpochSecond(Long.parseLong(value.toString())));
            } else if ("isAccepted".equals(key)) {
                finalExtendRequest.setIsAccepted((Boolean) value);
            }
        });

        extendRequest = extendPassTimeRequestRepository.save(finalExtendRequest);
        return extendPassTimeRequestMapper.entityToDTO(extendRequest);
    }

    @Transactional
    @Override
    public void deleteExtendPassTimeRequest(UUID userId, UUID extendPassRequestId) {
        ExtendPassTimeRequest extendRequest = extendPassTimeRequestRepository.findById(extendPassRequestId)
                .orElseThrow(() -> new PassRequestNotFoundException(extendPassRequestId));

        var passRequest = getRawPassRequest(extendRequest.getPassRequestId());

        if (!passRequest.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }

        if (extendRequest.getIsAccepted() != null) {
            throw new BadRequestException("Нельзя удалить уже рассмотренный запрос");
        }

        extendPassTimeRequestRepository.delete(extendRequest);
    }

    private Specification<PassRequest> getSpecByFilters(PassRequestFilters request) {
        return Specification.where(PassRequestSpecification.userEqual(request.getUser()))
                .and(PassRequestSpecification.userSearchStringLike(request.getUserSearchString()))
                .and(PassRequestSpecification.createDateBetween(
                        request.getCreateDateStart(),
                        request.getCreateDateEnd())
                )
                .and(PassRequestSpecification.dateBetweenStartAndEnd(request.getDate()))
                .and(PassRequestSpecification.isAcceptedEqual(request.getIsAccepted()))
                .and(PassRequestSpecification.userInGroup(request.getGroupNumber()));
    }

}

