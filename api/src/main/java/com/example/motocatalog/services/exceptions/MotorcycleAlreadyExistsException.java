package com.example.motocatalog.services.exceptions;

public class MotorcycleAlreadyExistsException extends RuntimeException {

    public MotorcycleAlreadyExistsException(String message) {
        super(message);
    }
}
