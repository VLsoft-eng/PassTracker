package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.enumeration.UserRole;
import backend.academy.passtracker.core.exception.BadRequestException;
import backend.academy.passtracker.core.mapper.RegistrationMapper;
import backend.academy.passtracker.core.config.security.userDetails.CustomUserDetails;
import backend.academy.passtracker.core.config.security.userDetails.CustomUserDetailsService;
import backend.academy.passtracker.core.service.AuthService;
import backend.academy.passtracker.core.service.JwtService;
import backend.academy.passtracker.core.service.UserService;
import backend.academy.passtracker.rest.model.LoginRequest;
import backend.academy.passtracker.rest.model.LoginResponse;
import backend.academy.passtracker.rest.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationMapper registrationMapper;

    public LoginResponse register(RegistrationRequest registrationRequest) {

        if (registrationRequest.userRole().equals(UserRole.ROLE_ADMIN)) {
            throw new BadRequestException("Нельзя зарегистрироваться с ролью 'админ'");
        }

        String hashedPassword = passwordEncoder.encode(registrationRequest.password());
        UserCreateDto userCreateDto = registrationMapper.toUserCreateDto(
                registrationRequest,
                hashedPassword
        );

        userService.createUser(userCreateDto);

        return login(
                new LoginRequest(
                        registrationRequest.email(),
                        registrationRequest.password()
                )
        );
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(request.email());

        String jwt = jwtService.generateToken(userDetails);
        return new LoginResponse(jwt);
    }
}

