package com.larramendi.login.system.controllers.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String msg) {
        super(msg);
    }
}
