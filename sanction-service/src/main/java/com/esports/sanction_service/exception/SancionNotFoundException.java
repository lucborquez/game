package com.esports.sanction_service.exception;

public class SancionNotFoundException extends RuntimeException {
    public SancionNotFoundException(Long id) {
        super("Sancion no encontrada con ID: " + id);
    }
}
