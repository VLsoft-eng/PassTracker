package backend.academy.passtracker.core.mapper.impl;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserCreateDto userCreateDto) {
        return User.builder()
                .email(userCreateDto.email())
                .password(userCreateDto.password())
                .fullName(userCreateDto.fullName())
                .role(userCreateDto.userRole())
                .build();
    }
}
