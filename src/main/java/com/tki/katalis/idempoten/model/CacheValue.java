package com.tki.katalis.idempoten.model;


import java.time.Duration;

public  class CacheValue {
    private final String value;
    private final Duration expiryDuration;

    public CacheValue(String value, Duration expiryDuration) {
        this.value = value;
        this.expiryDuration = expiryDuration;
    }

    public String getValue() {
        return value;
    }

    public Duration getExpiryDuration() {
        return expiryDuration;
    }
}
