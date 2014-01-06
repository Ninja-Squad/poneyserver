package com.ninja_squad.poneyserver.web;

/**
 * Exception thrown when a bad request must be sent back
 * @author JB Nizet
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
