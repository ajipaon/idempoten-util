package com.tki.katalis.idempoten.config;


public interface IdempotentToken {

    boolean add(String key, String value, Long expireTime);

    void remove(String key, String value);

}
