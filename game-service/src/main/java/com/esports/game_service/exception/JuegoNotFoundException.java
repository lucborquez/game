package com.esports.game_service.exception;

public class JuegoNotFoundException extends RuntimeException {
    public JuegoNotFoundException(Long id) {
        super("Juego no encontrado: " + id);
    }
}
