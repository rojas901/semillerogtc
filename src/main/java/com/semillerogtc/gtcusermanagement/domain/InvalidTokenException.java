package com.semillerogtc.gtcusermanagement.domain;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("token invalido");
    }
}
