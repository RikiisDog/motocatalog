package com.example.motocatalog.services.exceptions;

public class MotorcycleDuplicateUpdateException extends RuntimeException {
    
    public  MotorcycleDuplicateUpdateException(String message) {
        super(message);
    }
}
