package com.larramendi.login.system.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String msg) {
        super(msg);
    }
}
