package com.swa.payment_container.limiter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    private static final int LIMIT = 10;
    private static final int WINDOW_SECONDS = 60;

    public boolean isAllowed(UUID userId) {
        String key = "rate_limit:" + userId;

        long count = redisTemplate.opsForValue().increment(key);

        if (count == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(WINDOW_SECONDS));
        }

        return count <= LIMIT;
    }
}
