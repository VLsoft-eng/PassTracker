package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.core.exception.GroupNotFoundException;
import backend.academy.passtracker.core.mapper.GroupMapper;
import backend.academy.passtracker.core.repository.GroupRepository;
import backend.academy.passtracker.core.service.GroupService;
import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Transactional(readOnly = true)
    @Override
    public GroupDTO getGroupById(Long groupNumber) {
        return groupMapper.entityToDTO(
                getRawGroupById(groupNumber)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupDTO> getGroups(Boolean isDeleted) {
        return groupRepository.findAllByIsDeleted(isDeleted != null && isDeleted).stream()
                .map(groupMapper::entityToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Group getRawGroupById(Long groupNumber) {
        return groupRepository.findById(groupNumber)
                .orElseThrow(() -> new GroupNotFoundException(groupNumber));
    }

    @Transactional
    @Override
    public GroupDTO createGroup(CreateGroupRequest createGroupRequest) {
        return groupMapper.entityToDTO(
                groupRepository.save(
                        Group.builder()
                                .groupNumber(createGroupRequest.groupNumber())
                                .isDeleted(false)
                                .build()
                )
        );
    }

    @Transactional
    @Override
    public GroupDTO deleteGroup(Long groupId) {
        var group = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        group.setIsDeleted(true);
        return groupMapper.entityToDTO(groupRepository.save(group));
    }
}
