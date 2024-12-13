package com.tki.katalis.idempoten.config;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    String message() default "on processing";

    long expiredTime() default 3L;

    String prefixKey()  default "";

    boolean useCredential() default false;

}