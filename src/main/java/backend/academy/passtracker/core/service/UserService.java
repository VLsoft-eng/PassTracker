package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.dto.UserCreateDto;

import java.util.UUID;

public interface UserService {
    public void createUser(UserCreateDto userCreateDto);
}