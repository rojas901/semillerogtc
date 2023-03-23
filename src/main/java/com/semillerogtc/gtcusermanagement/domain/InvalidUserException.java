package com.semillerogtc.gtcusermanagement.domain;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException() {
        super("Email y/o password invalido");
    }
}
