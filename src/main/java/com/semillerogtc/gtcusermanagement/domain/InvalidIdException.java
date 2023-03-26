package com.semillerogtc.gtcusermanagement.domain;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException() {
        super("id invalido");
    }
}
