package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.config.security.userDetails.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(CustomUserDetails user);

    boolean isTokenValid(String token, UserDetails userDetails);

    void banToken(UUID tokenId, long ttlms);

    Boolean isTokenBanned(UUID tokenId);

    UUID extractTokenId(String token);
}
