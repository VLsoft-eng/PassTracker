package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.core.exception.BadRequestException;
import backend.academy.passtracker.core.exception.ForbiddenException;
import backend.academy.passtracker.core.mapper.RegistrationMapper;
import backend.academy.passtracker.core.mapper.UserMapper;
import backend.academy.passtracker.core.service.AdminService;
import backend.academy.passtracker.core.service.FacultyService;
import backend.academy.passtracker.core.service.GroupService;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.rest.model.auth.RegistrationRequest;
import backend.academy.passtracker.rest.model.faculty.CreateFacultyRequest;
import backend.academy.passtracker.rest.model.faculty.FacultyDTO;
import backend.academy.passtracker.rest.model.group.CreateGroupRequest;
import backend.academy.passtracker.rest.model.group.GroupDTO;
import backend.academy.passtracker.rest.model.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final GroupService groupService;
    private final FacultyService facultyService;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationMapper registrationMapper;
    private final UserMapper userMapper;

    @Override
    public FacultyDTO createFaculty(CreateFacultyRequest createFacultyRequest) {
        return facultyService.createFaculty(createFacultyRequest);
    }

    @Override
    public void deleteFaculty(UUID facultyId) {
        facultyService.deleteFaculty(facultyId);
    }

    @Override
    public GroupDTO createGroup(CreateGroupRequest createGroupRequest) {
        return groupService.createGroup(createGroupRequest);
    }

    @Override
    public GroupDTO deleteGroup(Long groupId) {
        return groupService.deleteGroup(groupId);
    }

    @Override
    public Page<UserDTO> getUsers(String fullName, String email, Boolean isAccepted, Pageable pageable) {
        return userService.getUsers(fullName, email, isAccepted, pageable);
    }

    @Override
    public UserDTO createUser(RegistrationRequest registrationRequest) {
        if (registrationRequest.userRole().equals(UserRole.ROLE_ADMIN)) {
            throw new BadRequestException("Нельзя зарегистрироваться с ролью 'админ'");
        }

        String hashedPassword = passwordEncoder.encode(registrationRequest.password());
        UserCreateDto userCreateDto = registrationMapper.toUserCreateDto(
                registrationRequest,
                hashedPassword
        );

        return userService.createUser(userCreateDto);
    }

    @Override
    public UserDTO updateUserActivation(UUID userId, Boolean isAccepted) {
        var user = userService.getRawUser(userId);

        if (user.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        if (user.getIsAccepted().equals(isAccepted)) {
            return userMapper.entityToDTO(user);
        }

        return userService.updateUserActivation(user, isAccepted);
    }
}
