package backend.academy.passtracker.core.mapper.impl;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User toEntity(UserCreateDto userCreateDto) {
        return User.builder()
                .email(userCreateDto.email())
                .password(passwordEncoder.encode(userCreateDto.password()))
                .fullName(userCreateDto.fullName())
                .role(userCreateDto.userRole())
                .build();
    }
}
