package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.config.security.userDetails.CustomUserDetails;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.rest.model.user.UserDTO;
import backend.academy.passtracker.rest.model.user.UserPatchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Контроллер, отвечающий за пользователей в системе")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Профиль",
            description = "Позволяет пользователю просмотреть профиль"
    )
    @GetMapping("/profile")
    private UserDTO getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return userService.getUser(userDetails.getId());
    }

    @Operation(
            summary = "Изменение профиля",
            description = "Позволяет пользователю изменить профиль"
    )
    @PatchMapping("/profile")
    public UserDTO updateUserPartially(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody UserPatchDTO updates
    ) {
        return userService.updateUserPartially(customUserDetails.getId(), updates);
    }

    @Operation(
            summary = "Изменение роли пользователя (деканат)",
            description = "Позволяет админу изменить роль пользователя"
    )
    @PatchMapping("/{userId}/role")
    private UserDTO changeUserRole(
            @PathVariable("userId") UUID userId,
            @RequestParam UserRole role
    ) {
        return userService.changeUserRole(userId, role);
    }

    @Operation(
            summary = "Получение пользователей по параметрам (деканат, преподаватель)",
            description = "Позволяет получить страницу пользователей в системе по параметрам"
    )
    @GetMapping
    private Page<UserDTO> getUsers(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long groupNumber,
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) Boolean isBlocked,
            @PageableDefault(size = 10, page = 0, sort = "fullName", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return userService.getUsers(fullName, email, groupNumber, role, isBlocked, pageable);
    }

}
