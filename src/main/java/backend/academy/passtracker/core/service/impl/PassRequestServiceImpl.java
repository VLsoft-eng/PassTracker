package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.dto.PassRequestFilters;
import backend.academy.passtracker.core.entity.ExtendPassTimeRequest;
import backend.academy.passtracker.core.entity.MinioFile;
import backend.academy.passtracker.core.entity.PassRequest;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.core.exception.BadRequestException;
import backend.academy.passtracker.core.exception.ForbiddenException;
import backend.academy.passtracker.core.exception.PassRequestNotFoundException;
import backend.academy.passtracker.core.exception.TooMuchFilesException;
import backend.academy.passtracker.core.mapper.ExtendPassTimeRequestMapper;
import backend.academy.passtracker.core.mapper.PassRequestMapper;
import backend.academy.passtracker.core.message.ExceptionMessage;
import backend.academy.passtracker.core.repository.ExtendPassTimeRequestRepository;
import backend.academy.passtracker.core.repository.PassRequestRepository;
import backend.academy.passtracker.core.service.MinioFileService;
import backend.academy.passtracker.core.service.PassRequestService;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.core.specification.PassRequestSpecification;
import backend.academy.passtracker.rest.model.pass.request.*;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    private final MinioFileService minioFileService;

    @Value("${file-upload.max-files-count}")
    private int maxFileCount;

    @Transactional(readOnly = true)
    @Override
    public PassRequestDTO getPassRequest(UUID userId, UUID requestId) {
        var user = userService.getUser(userId);
        var request = passRequestMapper.entityToDTO(getRawPassRequest(requestId));

        if (user.getRole().equals(UserRole.ROLE_STUDENT) && !request.getUser().equals(user)) {
            throw new ForbiddenException();
        }

        return request;
    }

    @Transactional(readOnly = true)
    @Override
    public PassRequest getRawPassRequest(UUID requestId) {
        return passRequestRepository.findById(requestId)
                .orElseThrow(() -> new PassRequestNotFoundException(requestId));
    }

    @Transactional(readOnly = true)
    @Override
    public ExtendPassTimeRequest getRawExtendPassTimeRequest(UUID requestId) {
        return extendPassTimeRequestRepository.findById(requestId)
                .orElseThrow(() -> new PassRequestNotFoundException(requestId));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ShortPassRequestDTO> getPassRequests(
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

        return passRequestRepository.findAll(spec, pageable).map(passRequestMapper::entityToShortDTO);
    }

    @Override
    public Page<ShortPassRequestDTO> getMyPassRequests(UUID userId, Boolean isAccepted, Pageable pageable) {
        var user = userService.getRawUser(userId);

        if (user.getStudentGroup() == null) {
            throw new ForbiddenException(ExceptionMessage.REQUIRED_GROUP);
        }

        PassRequestFilters request = PassRequestFilters.builder()
                .user(user)
                .isAccepted(isAccepted)
                .build();

        Specification<PassRequest> spec = getSpecByFilters(request);

        return passRequestRepository.findAll(spec, pageable).map(passRequestMapper::entityToShortDTO);
    }

    @Transactional
    @Override
    public PassRequestDTO createPassRequest(
            UUID userId,
            PassRequestRequest request,
            List<MultipartFile> files
    ) throws MinioException {
        var user = userService.getRawUser(userId);

        if (user.getStudentGroup() == null) {
            throw new ForbiddenException(ExceptionMessage.REQUIRED_GROUP);
        }

        if (files != null && files.size() > maxFileCount) {
            throw new TooMuchFilesException(String
                    .format("Максимальное количество файлов в запросе равно %s", maxFileCount));
        }

        PassRequest passRequest = PassRequest.builder()
                .id(UUID.randomUUID())
                .user(user)
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .minioFiles(List.of())
                .isAccepted(false)
                .createTimestamp(Instant.now())
                .build();

        List<MinioFile> minioFiles = new ArrayList<>();

        if (files != null && !files.isEmpty()) {
            for (MultipartFile minioFile : files) {
                var file = minioFileService.uploadFile(minioFile, userId);
                if (file != null) {
                    minioFiles.add(file);
                }
            }
        }

        passRequest.setMinioFiles(minioFiles);

        passRequest = passRequestRepository.save(passRequest);
        return passRequestMapper.entityToDTO(passRequest);
    }

    @Transactional
    @Override
    public PassRequestDTO updatePassRequest(UUID userId, UUID passRequestId, Map<String, Object> updates) {
        PassRequest passRequest = passRequestRepository.findById(passRequestId)
                .orElseThrow(() -> new PassRequestNotFoundException(passRequestId));
        var user = userService.getRawUser(userId);

        if (!passRequest.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }

        if (user.getStudentGroup() == null) {
            throw new ForbiddenException(ExceptionMessage.REQUIRED_GROUP);
        }

        if (passRequest.getIsAccepted()) {
            throw new BadRequestException("Нельзя изменить уже рассмотренный запрос");
        }

        PassRequest finalPassRequest = passRequest;
        updates.forEach((key, value) -> {
            switch (key) {
                case "dateStart" ->
                        finalPassRequest.setDateStart(Instant.ofEpochSecond(Long.parseLong(value.toString())));
                case "dateEnd" -> finalPassRequest.setDateEnd(Instant.ofEpochSecond(Long.parseLong(value.toString())));
                case "minioFile" -> finalPassRequest.setMinioFiles(null);
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
    public ExtendPassTimeRequestDTO createExtendPassTimeRequest(
            UUID userId,
            ExtendPassTimeRequestRequest request,
            List<MultipartFile> files
    ) throws MinioException {
        var passRequest = getRawPassRequest(request.getPassRequestId());

        if (!passRequest.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }

        if (files != null && files.size() > maxFileCount) {
            throw new TooMuchFilesException(String
                    .format("Максимальное количество файлов в запросе равно %s", maxFileCount));
        }

        ExtendPassTimeRequest extendRequest = ExtendPassTimeRequest.builder()
                .id(UUID.randomUUID())
                .passRequestId(request.getPassRequestId())
                .dateEnd(request.getDateEnd())
                .isAccepted(false)
                .createTimestamp(Instant.now())
                .build();

        List<MinioFile> minioFiles = new ArrayList<>();

        if (files != null && !files.isEmpty()) {
            for (MultipartFile minioFile : files) {
                minioFiles.add(minioFileService.uploadFile(minioFile, userId));
            }
        }

        extendRequest.setMinioFiles(minioFiles);

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

    @Transactional
    @Override
    public PassRequestDTO processPassRequest(UUID requestId, Boolean isAccepted) {
        var request = getRawPassRequest(requestId);

        request.setIsAccepted(isAccepted);

        return passRequestMapper.entityToDTO(passRequestRepository.save(request));
    }

    @Transactional
    @Override
    public PassRequestDTO processExtendPassTimeRequest(UUID requestId, Boolean isAccepted) {
        var extendRequest = getRawExtendPassTimeRequest(requestId);
        extendRequest.setIsAccepted(isAccepted);

        var request = getRawPassRequest(extendRequest.getPassRequestId());
        request.setDateEnd(extendRequest.getDateEnd());

        extendPassTimeRequestRepository.save(extendRequest);
        return passRequestMapper.entityToDTO(passRequestRepository.save(request));
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

