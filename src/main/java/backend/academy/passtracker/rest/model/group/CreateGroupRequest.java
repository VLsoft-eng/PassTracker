package backend.academy.passtracker.rest.model.group;

import java.util.UUID;

public record CreateGroupRequest(Long groupNumber, UUID facultyId) {
}
