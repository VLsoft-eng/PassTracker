package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    GroupDTO getGroupById(Long groupNumber);

    Group getRawGroupById(Long groupNumber);

    List<GroupDTO> getGroupsByFacultyId(UUID facultyId, Boolean isDeleted);

    GroupDTO createGroup(CreateGroupRequest createGroupRequest);

    GroupDTO deleteGroup(Long groupId);

}
