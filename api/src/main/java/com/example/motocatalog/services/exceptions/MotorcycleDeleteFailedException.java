package com.example.motocatalog.services.exceptions;

public class MotorcycleDeleteFailedException extends RuntimeException {

    public MotorcycleDeleteFailedException(String message) {
        super(message);
    }
}
