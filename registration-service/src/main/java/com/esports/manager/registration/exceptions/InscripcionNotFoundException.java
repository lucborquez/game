package com.esports.manager.registration.exceptions;

// Excepción personalizada cuando no se encuentra una inscripción
public class InscripcionNotFoundException extends RuntimeException {

    // Mensaje de error con el ID de la inscripción no encontrada
    public InscripcionNotFoundException(Long id) {
        super("Inscripción no encontrada con ID: " + id);
    }
}