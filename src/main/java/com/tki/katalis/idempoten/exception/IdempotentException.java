package com.tki.katalis.idempoten.exception;


public class IdempotentException extends RuntimeException {

    public IdempotentException(String message) {
        super(message);
    }

}
