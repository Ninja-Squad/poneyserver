package com.ninja_squad.poneyserver.web;

import com.google.common.base.Throwables;
import com.ninja_squad.poneyserver.web.security.UnauthorizedException;
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
    public ApiError handleBadRequest(BadRequestException e) {
        return new ApiError(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ApiError handleBadRequest(UnauthorizedException e) {
        return new ApiError("Authentication needed to access this resource");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiError handleException(Exception e) {
        return new ApiError("Unexpected exception: " + Throwables.getStackTraceAsString(e));
    }
}
