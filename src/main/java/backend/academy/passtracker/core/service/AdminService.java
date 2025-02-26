package backend.academy.passtracker.core.service;

import backend.academy.passtracker.rest.model.auth.RegistrationRequest;
import backend.academy.passtracker.rest.model.faculty.CreateFacultyRequest;
import backend.academy.passtracker.rest.model.faculty.FacultyDTO;
import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import backend.academy.passtracker.rest.model.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminService {

    FacultyDTO createFaculty(CreateFacultyRequest createFacultyRequest);

    void deleteFaculty(UUID facultyId);

    GroupDTO createGroup(CreateGroupRequest createGroupRequest);

    GroupDTO deleteGroup(Long groupId);

    Page<UserDTO> getUsers(String fullName, String email, Boolean isAccepted, Pageable pageable);

    UserDTO createUser(RegistrationRequest registrationRequest);

    UserDTO updateUserActivation(UUID userId, Boolean isAccepted);

}
