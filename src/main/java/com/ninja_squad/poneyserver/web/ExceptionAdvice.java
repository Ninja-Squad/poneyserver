package com.ninja_squad.poneyserver.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ControllerAdvice used to handle exceptions
 * @author JB Nizet
 */
@ControllerAdvice
public class ExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public String handleBadRequest(BadRequestException e) {
        return e.getMessage();
    }
}
