package com.ninja_squad.poneyserver.web;

/**
 * Object sent as a JSON error response
 * @author JB Nizet
 */
public final class ApiError {
    private String message;

    public ApiError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
