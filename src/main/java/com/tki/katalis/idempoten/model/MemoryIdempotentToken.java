package com.tki.katalis.idempoten.model;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.tki.katalis.idempoten.config.IdempotentToken;
import java.time.Duration;
import java.util.Objects;

public class MemoryIdempotentToken implements IdempotentToken {


    private static final String DEFAULT_VALUE = "0";


    private Cache<String, CacheValue> cache = Caffeine.newBuilder()
            .initialCapacity(10)
            .maximumSize(1000)
            .expireAfter(new Expiry<String, CacheValue>() {

                @Override
                public long expireAfterCreate(String key, CacheValue value, long currentTime) {
                    return value.getExpiryDuration().toNanos();
                }

                @Override
                public long expireAfterUpdate(String key, CacheValue value, long currentTime, long currentDuration) {
                    return currentDuration;
                }

                @Override
                public long expireAfterRead(String key, CacheValue value, long currentTime, long currentDuration) {
                    return currentDuration;
                }
            })
            .build();

    @Override
    public boolean add(String key, String value, Long expireTime) {
        try {
            Object o = cache.getIfPresent(key);
            if (Objects.isNull(o)) {
                CacheValue cacheValue = new CacheValue(value, Duration.ofSeconds(expireTime));
                cache.put(key, cacheValue);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Memory idempotent token [ " + key + " ] set error.");
            return false;
        }
    }

    @Override
    public void remove(String key, String value) {
        Object v = cache.getIfPresent(key);
        if (value.equals(v)) {
            cache.invalidate(key);
        }
    }

}
