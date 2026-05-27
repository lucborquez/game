package com.esports.manager.match.exceptions;

public class PartidaNotFoundException extends RuntimeException {

    //mensaje de ERROR con el id de LA PARTIDA NO ENCONTRADA
    public PartidaNotFoundException(Long id) {
        super("Partida no encontrada con ID: " + id);
    }
}
