package com.semillerogtc.gtcusermanagement.domain;

public class InvalidNameException extends RuntimeException{

    public InvalidNameException() {
        super("nombre invalido");
    }
}
