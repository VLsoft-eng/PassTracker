package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.rest.model.user.UserDTO;
import backend.academy.passtracker.rest.model.user.UserPatchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {
    void createUser(UserCreateDto userCreateDto);

    UserDTO getUser(UUID userId);

    User getRawUser(UUID userId);

    Page<UserDTO> getUsers(
            String fullName,
            String email,
            Long groupNumber,
            UserRole role,
            Boolean isBlocked,
            Pageable pageable
    );

    UserDTO updateUserPartially(UUID userId, UserPatchDTO updates);

    UserDTO changeUserRole(UUID userId, UserRole role);

    UserDTO changeUserBlock(UUID userId, Boolean isBlocked);
}