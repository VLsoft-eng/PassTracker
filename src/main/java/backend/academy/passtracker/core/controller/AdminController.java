package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.service.AdminService;
import backend.academy.passtracker.rest.model.auth.RegistrationRequest;
import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import backend.academy.passtracker.rest.model.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

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
        @PageableDefault(size = 10, page = 0, sort = "full_name", direction = Sort.Direction.DESC)
        Pageable pageable
    ) {
        return adminService.getUsers(fullName, email, pageable);
    }

}
