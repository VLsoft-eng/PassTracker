package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.service.AdminService;
import backend.academy.passtracker.rest.model.auth.RegistrationRequest;
import backend.academy.passtracker.rest.model.faculty.CreateFacultyRequest;
import backend.academy.passtracker.rest.model.faculty.FacultyDTO;
import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import backend.academy.passtracker.rest.model.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/faculty")
    private FacultyDTO createFaculty(
            @RequestBody CreateFacultyRequest createFacultyRequest
    ) {
        return adminService.createFaculty(createFacultyRequest);
    }

    @PostMapping("/group")
    private GroupDTO createGroup(
            @RequestBody CreateGroupRequest createGroupRequest
    ) {
        return adminService.createGroup(createGroupRequest);
    }

    @DeleteMapping("/group")
    private GroupDTO deleteGroup(
            @RequestParam Long groupId
    ) {
        return adminService.deleteGroup(groupId);
    }

    @GetMapping("/users")
    private Page<UserDTO> getUsers(
        @RequestParam String fullName,
        @RequestParam String email,
        @RequestParam Boolean isAccepted,
        @PageableDefault(size = 10, page = 0, sort = "full_name", direction = Sort.Direction.DESC)
        Pageable pageable
    ) {
        return adminService.getUsers(fullName, email, isAccepted, pageable);
    }

    @PostMapping("/user")
    private UserDTO createUser(
            @RequestBody RegistrationRequest registrationRequest
            ) {
        return adminService.createUser(registrationRequest);
    }

    @PatchMapping("/update-user-activation")
    private UserDTO updateUserActivation(
            @RequestParam UUID userId,
            @RequestParam Boolean isAccepted
    ) {
        return adminService.updateUserActivation(userId, isAccepted);
    }

}
