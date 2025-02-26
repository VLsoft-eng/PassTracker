package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.core.exception.BadRequestException;
import backend.academy.passtracker.core.exception.EmailAlreadyUsedException;
import backend.academy.passtracker.core.exception.UserNotFoundException;
import backend.academy.passtracker.core.mapper.UserMapper;
import backend.academy.passtracker.core.message.ExceptionMessage;
import backend.academy.passtracker.core.repository.UserRepository;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.rest.model.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDTO createUser(UserCreateDto userCreateDto) {
        if (userRepository.existsByEmail(userCreateDto.email())) {
            throw new EmailAlreadyUsedException(ExceptionMessage.EMAIL_ALREADY_USED);
        }

        User user = userMapper.createDTOtoEntity(userCreateDto);

        return userMapper.entityToDTO(userRepository.save(user));
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
            Boolean isAccepted,
            Pageable pageable
    ) {
        return userRepository.findAllUsersBySearchStrings(
                fullName,
                email,
                isAccepted,
                pageable
        ).map(userMapper::entityToDTO);
    }

    @Transactional
    @Override
    public UserDTO updateUserActivation(User user, Boolean isAccepted) {
        user.setIsAccepted(isAccepted);
        return userMapper.entityToDTO(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserDTO updateUserPartially(UUID userId, Map<String, Object> updates) {
        User user = getRawUser(userId);

        updates.forEach((key, value) -> {
            switch (key) {
                case "fullName" -> user.setFullName((String) value);
                case "email" -> {
                    if (userRepository.existsByEmail((String) value)) {
                        throw new EmailAlreadyUsedException(ExceptionMessage.EMAIL_ALREADY_USED);
                    }
                    user.setEmail((String) value);
                }
                case "password" -> user.setPassword(passwordEncoder.encode((String) value));
                case "role" -> {
                    var role = UserRole.fromValue((String) value);
                    if (role.equals(UserRole.ROLE_ADMIN)) {
                        throw new BadRequestException("Нельзя поставить себе роль админа");
                    }
                    user.setRole(role);
                    if (role.equals(UserRole.ROLE_TEACHER) || role.equals(UserRole.ROLE_DEANERY)) {
                        user.setIsAccepted(null);
                    } else if (role.equals(UserRole.ROLE_STUDENT)) {
                        user.setIsAccepted(true);
                    }
                }
                default -> throw new IllegalArgumentException("Поле " + key + " нельзя обновить.");
            }
        });

        return userMapper.entityToDTO(userRepository.save(user));
    }
}
