package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.service.GroupService;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    private GroupDTO getGroupById(
            @RequestParam Long groupId
    ) {
        return groupService.getGroupById(groupId);
    }

    @GetMapping("/list")
    private List<GroupDTO> getGroups(
            @RequestParam(required = false) Boolean isDeleted
    ) {
        return groupService.getGroups(isDeleted);
    }

}
