package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    GroupDTO getGroupById(Long groupNumber);

    List<GroupDTO> getGroups(Boolean isDeleted);

    Group getRawGroupById(Long groupNumber);

    GroupDTO createGroup(CreateGroupRequest createGroupRequest);

    GroupDTO deleteGroup(Long groupId);

}
