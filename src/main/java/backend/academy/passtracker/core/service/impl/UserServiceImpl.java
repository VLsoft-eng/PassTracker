package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.dto.UserFilters;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.core.exception.BadRequestException;
import backend.academy.passtracker.core.exception.EmailAlreadyUsedException;
import backend.academy.passtracker.core.exception.UserNotFoundException;
import backend.academy.passtracker.core.mapper.UserMapper;
import backend.academy.passtracker.core.message.ExceptionMessage;
import backend.academy.passtracker.core.repository.UserRepository;
import backend.academy.passtracker.core.service.GroupService;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.core.specification.UserSpecification;
import backend.academy.passtracker.rest.model.user.UserDTO;
import backend.academy.passtracker.rest.model.user.UserPatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final GroupService groupService;

    @Transactional
    @Override
    public void createUser(UserCreateDto userCreateDto) {
        if (userRepository.existsByEmail(userCreateDto.email())) {
            throw new EmailAlreadyUsedException(ExceptionMessage.EMAIL_ALREADY_USED);
        }

        User user = userMapper.createDTOtoEntity(userCreateDto);

        userMapper.entityToDTO(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.entityToDTO(getRawUser(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public User getRawUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDTO> getUsers(
            String fullName,
            String email,
            Long groupNumber,
            UserRole role,
            Boolean isBlocked,
            Pageable pageable
    ) {
        var filters = UserFilters.builder()
                .fullName(fullName)
                .email(email)
                .groupNumber(groupNumber)
                .role(role)
                .isBlocked(isBlocked)
                .build();

        var spec = getSpecByFilters(filters);

        return userRepository.findAll(spec, pageable).map(userMapper::entityToDTO);
    }

    @Transactional
    @Override
    public UserDTO updateUserPartially(UUID userId, UserPatchDTO updates) {
        User user = getRawUser(userId);

        if (updates.getFullName() != null) {
            user.setFullName(updates.getFullName());
        }
        if (updates.getEmail() != null) {
            user.setEmail(updates.getEmail());
        }
        if (updates.getGroup() != null) {
            var group = groupService.getRawGroupById(updates.getGroup());

            if (group.getIsDeleted()) {
                throw new BadRequestException("Группы " + updates.getGroup().toString() + " больше не существует");
            }

            user.setStudentGroup(group);
        }

        return userMapper.entityToDTO(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserDTO changeUserRole(UUID myUserId, UUID userId, UserRole role) {
        if (role.equals(UserRole.ROLE_ADMIN)) {
            throw new BadRequestException("Нельзя поставить роль админа");
        }

        var me = getRawUser(myUserId);

        if (role.equals(UserRole.ROLE_DEANERY) && !me.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new BadRequestException("Нельзя поставить роль декана");
        }

        var user = getRawUser(userId);

        user.setRole(role);
        return userMapper.entityToDTO(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserDTO changeUserBlock(UUID userId, Boolean isBlocked) {
        var user = getRawUser(userId);

        if (user.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new BadRequestException("Нельзя заблокировать админа");
        }

        user.setIsBlocked(isBlocked);
        return userMapper.entityToDTO(userRepository.save(user));
    }

    private Specification<User> getSpecByFilters(UserFilters request) {
        return Specification.where(UserSpecification.emailLike(request.getEmail()))
                .and(UserSpecification.fullNameLike(request.getFullName()))
                .and(UserSpecification.groupNumberEqual(request.getGroupNumber()))
                .and(UserSpecification.roleEqual(request.getRole()))
                .and(UserSpecification.isBlockedEqual(request.getIsBlocked()));
    }
}
