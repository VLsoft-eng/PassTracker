package backend.academy.passtracker.core.service;

import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import backend.academy.passtracker.rest.model.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdminService {

    GroupDTO createGroup(CreateGroupRequest createGroupRequest);

    GroupDTO deleteGroup(Long groupId);

    Page<UserDTO> getUsers(
            String fullName,
            String email,
            Pageable pageable
    );

}
