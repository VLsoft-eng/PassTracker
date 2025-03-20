package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.service.PassRequestService;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.rest.model.pass.request.PassRequestDTO;
import backend.academy.passtracker.rest.model.user.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dean")
@RequiredArgsConstructor
@Tag(name = "Деканат", description = "Контроллер, отвечающий за возможности деканата в приложении")
public class DeanController {

    private final PassRequestService passRequestService;
    private final UserService userService;

    @Operation(
            summary = "Рассмотреть заявку на пропуск",
            description = "Позволяет деканату рассмотреть заявку"
    )
    @PutMapping("/pass/request/{requestId}")
    private PassRequestDTO processPassRequest(
            @PathVariable("requestId") UUID requestId,
            @RequestParam Boolean isAccepted
    ) {
        return passRequestService.processPassRequest(requestId, isAccepted);
    }

    @Operation(
            summary = "Рассмотреть заявку на продление пропуска",
            description = "Позволяет деканату рассмотреть заявку"
    )
    @PutMapping("/pass/request/extend/{requestId}")
    private PassRequestDTO processExtendPassTimeRequest(
            @PathVariable("requestId") UUID requestId,
            @RequestParam Boolean isAccepted
    ) {
        return passRequestService.processExtendPassTimeRequest(requestId, isAccepted);
    }

    @Operation(
            summary = "Заблокировать/разблокировать пользователя",
            description = "Позволяет деканату изменить блокировку пользователя"
    )
    @PutMapping("/block/user/{userId}")
    private UserDTO changeUserBlock(
            @PathVariable("userId") UUID userId,
            @RequestParam Boolean isBlocked
    ) {
        return userService.changeUserBlock(userId, isBlocked);
    }

}
