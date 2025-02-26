package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.rest.model.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserDTO createUser(UserCreateDto userCreateDto);

    User getRawUser(UUID userId);

    Page<UserDTO> getUsers(String fullName, String email, Boolean isAccepted, Pageable pageable);

    UserDTO updateUserActivation(User user, Boolean isAccepted);
}