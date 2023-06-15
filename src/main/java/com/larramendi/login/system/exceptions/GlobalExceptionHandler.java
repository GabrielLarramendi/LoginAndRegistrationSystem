package com.larramendi.login.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        ErrorDetails errorDetails = new ErrorDetails("Ocorreu um erro interno.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return errorDetails.getMessage();
    }

    @ExceptionHandler(IdNotFoundException.class)
    public String handlerIdNotFoundException(IdNotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return errorDetails.getMessage();
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handlerEmailAlreadyExistsException(EmailAlreadyExistsException e, Model model) {
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("emailError", errorDetails.getMessage());
        return "register-form";
    }
}