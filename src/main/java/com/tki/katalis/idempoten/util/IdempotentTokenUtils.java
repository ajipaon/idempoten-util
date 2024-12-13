package com.tki.katalis.idempoten.util;

import com.tki.katalis.idempoten.config.IdempotentToken;
import org.springframework.beans.factory.annotation.Autowired;

public class IdempotentTokenUtils {

    private static IdempotentToken idempotentToken;


    public static boolean setIfNotFound(String key, String value, Long expireTime) {
        return idempotentToken.add(key, value, expireTime);
    }

    public static void remove(String key, String value) {
         idempotentToken.remove(key, value);
    }

    @Autowired
    public void setIdempotentToken(IdempotentToken idempotentToken) {
        IdempotentTokenUtils.idempotentToken = idempotentToken;
    }

}
