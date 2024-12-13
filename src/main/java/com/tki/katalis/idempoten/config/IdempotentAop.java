package com.tki.katalis.idempoten.config;

import com.tki.katalis.idempoten.exception.IdempotentException;
import com.tki.katalis.idempoten.util.IdempotentTokenUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


@Aspect
public class IdempotentAop {

    @Around("@annotation(idempotent)")
    public Object around(ProceedingJoinPoint pjp, Idempotent idempotent) throws IllegalAccessException {
//        String key = getTokenKey(pjp);
        Object result = null;
        String prefixKey = null;

        if(idempotent.useCredential()){
            Map<String, Object> payload = getCredential(pjp);
            prefixKey = payload.get("credentials").toString();
        }else{
            Map<String, Object> payload = getPayload(pjp);
            prefixKey = payload.get(idempotent.prefixKey()).toString();
        }
        boolean success = IdempotentTokenUtils.setIfNotFound(prefixKey,prefixKey,idempotent.expiredTime());
        if (!success) {
            System.out.println("multiple requet with key:" + prefixKey);
            throw new IdempotentException(idempotent.message());
        }

        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            System.out.println("Idempotent aop around process error, key name: " + e.getMessage());
        }

        return result;

    }

    private Map<String, Object> getPayload(ProceedingJoinPoint pjp) throws IllegalAccessException {
        Object payload = pjp.getArgs()[0];
        return objectToMap(payload);
    }

    private Map<String, Object> getCredential(ProceedingJoinPoint pjp) throws IllegalAccessException {

        Object credential = pjp.getArgs()[1];
        return objectToMap(credential);
    }

    private String getTokenKey(ProceedingJoinPoint pjp) {
        String methodName = pjp.getSignature().toString();
        String argsList =  pjp.getArgs()[0].toString();
        return methodName + ":" + argsList;
    }

    private static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

}
