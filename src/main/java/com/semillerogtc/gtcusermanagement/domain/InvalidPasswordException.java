package com.semillerogtc.gtcusermanagement.domain;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Password no válido");
    }
}
