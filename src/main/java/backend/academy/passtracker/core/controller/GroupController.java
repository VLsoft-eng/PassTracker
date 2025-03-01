package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.service.GroupService;
import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
@Tag(name = "Студенческие группы", description = "Контроллер, отвечающий за группы")
public class GroupController {

    private final GroupService groupService;

    @Operation(
            summary = "Получение группы по идентификатору",
            description = "Позволяет любому пользователю получить информацию о группе"
    )
    @GetMapping("/{groupId}")
    private GroupDTO getGroupById(
            @PathVariable Long groupId
    ) {
        return groupService.getGroupById(groupId);
    }

    @Operation(
            summary = "Получение списка групп",
            description = "Позволяет любому пользователю получить информацию о всех группах"
    )
    @GetMapping("/list")
    private List<GroupDTO> getGroups(
            @RequestParam(required = false) Boolean isDeleted
    ) {
        return groupService.getGroups(isDeleted);
    }

    @Operation(
            summary = "Создание группы (деканат)",
            description = "Позволяет создать группу"
    )
    @PostMapping
    private GroupDTO createGroup(
            @RequestBody CreateGroupRequest createGroupRequest
    ) {
        return groupService.createGroup(createGroupRequest);
    }

    @Operation(
            summary = "Удаление группы по идентификатору (деканат)",
            description = "Позволяет удалить группу"
    )
    @DeleteMapping
    private GroupDTO deleteGroup(
            @RequestParam Long groupId
    ) {
        return groupService.deleteGroup(groupId);
    }

}
