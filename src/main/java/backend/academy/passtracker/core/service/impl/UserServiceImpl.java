package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.exception.EmailAlreadyUsedException;
import backend.academy.passtracker.core.mapper.UserMapper;
import backend.academy.passtracker.core.message.ExceptionMessage;
import backend.academy.passtracker.core.repository.UserRepository;
import backend.academy.passtracker.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public void createUser(UserCreateDto userCreateDto) {
        if (userRepository.existsByEmail(userCreateDto.email())) {
            throw new EmailAlreadyUsedException(ExceptionMessage.EMAIL_ALREADY_USED);
        }

        User user = userMapper.createDTOtoEntity(userCreateDto);

        userRepository.save(user);
    }
}
