package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.security.userDetails.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(CustomUserDetails user);

    boolean isTokenValid(String token, UserDetails userDetails);
}
