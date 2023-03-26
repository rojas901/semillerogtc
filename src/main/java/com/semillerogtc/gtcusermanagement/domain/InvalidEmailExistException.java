package com.semillerogtc.gtcusermanagement.domain;

public class InvalidEmailExistException extends RuntimeException {

    public InvalidEmailExistException() {
        super("El email ya fue registrado");
    }
}
