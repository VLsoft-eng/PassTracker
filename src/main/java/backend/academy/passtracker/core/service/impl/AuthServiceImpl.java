package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.core.exception.BadRequestException;
import backend.academy.passtracker.core.mapper.RegistrationMapper;
import backend.academy.passtracker.core.config.security.userDetails.CustomUserDetails;
import backend.academy.passtracker.core.config.security.userDetails.CustomUserDetailsService;
import backend.academy.passtracker.core.service.AuthService;
import backend.academy.passtracker.core.service.GroupService;
import backend.academy.passtracker.core.service.JwtService;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.rest.model.auth.LoginRequest;
import backend.academy.passtracker.rest.model.auth.LoginResponse;
import backend.academy.passtracker.rest.model.auth.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final GroupService groupService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationMapper registrationMapper;

    @Transactional
    public LoginResponse register(RegistrationRequest registrationRequest) {

        if (registrationRequest.groupNumber() == null) {
            throw new BadRequestException("Не выбрана группа");
        }

        Group studentGroup = groupService.getRawGroupById(registrationRequest.groupNumber());

        String hashedPassword = passwordEncoder.encode(registrationRequest.password());
        UserCreateDto userCreateDto = registrationMapper.toUserCreateDto(
                registrationRequest,
                hashedPassword,
                studentGroup
        );

        userService.createUser(userCreateDto);

        return login(
                new LoginRequest(
                        registrationRequest.email(),
                        registrationRequest.password()
                )
        );
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(request.email());

        String jwt = jwtService.generateToken(userDetails);
        return new LoginResponse(jwt);
    }

    @Transactional
    public void logout() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() instanceof String token) {
            jwtService.banToken(token);
        }
    }
}

