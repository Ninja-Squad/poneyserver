package com.ninja_squad.poneyserver.web.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a 401 response must be sent back
 * @author JB Nizet
 */
@ResponseStatus(value= HttpStatus.UNAUTHORIZED)  // 401
public class UnauthorizedException extends RuntimeException {
}
