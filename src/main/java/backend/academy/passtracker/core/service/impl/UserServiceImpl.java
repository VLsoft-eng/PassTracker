package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
    public User getRawUser(UUID userId) {
        return userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDTO> getUsers(String fullName, String email, Boolean isAccepted, Pageable pageable) {
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
}
