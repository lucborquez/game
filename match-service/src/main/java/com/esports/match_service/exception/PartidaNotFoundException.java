package com.esports.match_service.exception;

public class PartidaNotFoundException extends RuntimeException {
    public PartidaNotFoundException(Long id) {
        super("Partida no encontrada con ID: " + id);
    }
}