package com.SanviiTechmet.h2dbcrud.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
