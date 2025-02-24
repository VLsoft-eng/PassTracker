package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;

public interface UserMapper {
    User toEntity(UserCreateDto userCreateDto);
}
