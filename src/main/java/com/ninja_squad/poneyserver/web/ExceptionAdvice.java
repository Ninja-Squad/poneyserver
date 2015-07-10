package com.ninja_squad.poneyserver.web;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ninja_squad.poneyserver.web.security.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ControllerAdvice used to handle exceptions
 * @author JB Nizet
 */
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(BadRequestException.class)
    public void handleBadRequest(BadRequestException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public void handleUnauthorized(UnauthorizedException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication needed to access this resource");
    }
}
