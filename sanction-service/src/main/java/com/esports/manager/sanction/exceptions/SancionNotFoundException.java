package com.esports.manager.sanction.exceptions;

public class SancionNotFoundException extends RuntimeException {

    //mensaje de error con el ID de la sancion no enconrtada
    public SancionNotFoundException(Long id) {
        super("Sancion no encontrada con id:" + id );
    }
}
