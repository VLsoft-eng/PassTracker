package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.core.exception.GroupNotFoundException;
import backend.academy.passtracker.core.mapper.GroupMapper;
import backend.academy.passtracker.core.repository.GroupRepository;
import backend.academy.passtracker.core.service.FacultyService;
import backend.academy.passtracker.core.service.GroupService;
import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final FacultyService facultyService;
    private final GroupMapper groupMapper;

    @Override
    public GroupDTO getGroupById(Long groupNumber) {
        return groupMapper.entityToDTO(
                groupRepository.findById(groupNumber)
                        .orElseThrow(() -> new GroupNotFoundException(groupNumber))
        );
    }

    @Override
    public List<GroupDTO> getGroupsByFacultyId(UUID facultyId, Boolean isDeleted) {
        return groupRepository.findAllByFacultyIdAndIsDeleted(facultyId, isDeleted)
                .stream()
                .map(groupMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GroupDTO createGroup(CreateGroupRequest createGroupRequest) {
        var faculty = facultyService.getRawFacultyById(createGroupRequest.facultyId());

        return groupMapper.entityToDTO(
                groupRepository.save(
                        Group.builder()
                                .groupNumber(createGroupRequest.groupNumber())
                                .faculty(faculty)
                                .isDeleted(false)
                                .build()
                )
        );
    }

    @Override
    public GroupDTO deleteGroup(Long groupId) {
        var group = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        group.setIsDeleted(true);
        return groupMapper.entityToDTO(groupRepository.save(group));
    }
}
