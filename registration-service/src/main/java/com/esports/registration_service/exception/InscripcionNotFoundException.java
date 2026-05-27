package com.esports.registration_service.exception;

public class InscripcionNotFoundException extends RuntimeException {
    public InscripcionNotFoundException(Long id) {
        super("Inscripcion no encontrada con ID: " + id);
    }
}