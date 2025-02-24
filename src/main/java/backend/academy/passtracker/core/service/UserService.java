package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.dto.UserCreateDto;

import java.util.UUID;

public interface UserService {
    public UUID createUser(UserCreateDto userCreateDto);
}