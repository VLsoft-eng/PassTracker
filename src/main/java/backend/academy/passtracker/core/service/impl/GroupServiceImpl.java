package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.exception.GroupNotFoundException;
import backend.academy.passtracker.core.mapper.GroupMapper;
import backend.academy.passtracker.core.repository.GroupRepository;
import backend.academy.passtracker.core.service.GroupService;
import backend.academy.passtracker.rest.model.GroupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public GroupDTO getGroupById(Long groupNumber) {
        return groupMapper.entityToDTO(
                groupRepository.findById(groupNumber)
                        .orElseThrow(() -> new GroupNotFoundException(groupNumber))
        );
    }

    @Override
    public List<GroupDTO> getGroupsByFacultyId(UUID facultyId) {
        return groupRepository.findAllByFacultyId(facultyId)
                .stream()
                .map(groupMapper::entityToDTO)
                .collect(Collectors.toList());
    }
}
