package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.service.AdminService;
import backend.academy.passtracker.core.service.GroupService;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import backend.academy.passtracker.rest.model.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final GroupService groupService;

    @Override
    public GroupDTO createGroup(CreateGroupRequest createGroupRequest) {
        return groupService.createGroup(createGroupRequest);
    }

    @Override
    public GroupDTO deleteGroup(Long groupId) {
        return groupService.deleteGroup(groupId);
    }

    @Override
    public Page<UserDTO> getUsers(
            String fullName,
            String email,
            Pageable pageable
    ) {
        return userService.getUsers(fullName, email, pageable);
    }

}
