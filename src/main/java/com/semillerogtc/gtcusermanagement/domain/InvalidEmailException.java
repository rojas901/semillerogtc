package com.semillerogtc.gtcusermanagement.domain;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("email no válido");
    }
}
