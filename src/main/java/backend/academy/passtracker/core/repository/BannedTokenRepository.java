package backend.academy.passtracker.core.repository;

import java.util.UUID;

public interface BannedTokenRepository {
    void addBannedToken(UUID tokenId, long ttlMs);
}
