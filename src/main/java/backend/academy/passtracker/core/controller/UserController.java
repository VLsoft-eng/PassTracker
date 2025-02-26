package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.config.security.userDetails.CustomUserDetails;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.rest.model.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    private UserDTO getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return userService.getUser(userDetails.getId());
    }


    @PatchMapping("/profile")
    public ResponseEntity<UserDTO> updateUserPartially(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.ok(userService.updateUserPartially(customUserDetails.getId(), updates));
    }
}
