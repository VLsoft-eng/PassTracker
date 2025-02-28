package backend.academy.passtracker.core.repository.impl;

import backend.academy.passtracker.core.repository.BannedTokenRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class BannedTokenRepositoryImpl implements BannedTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public BannedTokenRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void addBannedToken(UUID tokenId, long ttlMs) {
        redisTemplate.opsForValue().set(tokenId.toString(), "Blacklisted", ttlMs, TimeUnit.MILLISECONDS);
    }

    @Override
    public Boolean isTokenBanned(UUID tokenId) {
        return redisTemplate.opsForSet().isMember(tokenId.toString(), "Blacklisted");
    }
}
