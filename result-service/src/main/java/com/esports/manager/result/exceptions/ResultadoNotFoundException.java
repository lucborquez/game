package com.esports.manager.result.exceptions;

public class ResultadoNotFoundException extends RuntimeException {
    public ResultadoNotFoundException(Long id) {
        super("resultado no encontrado: " + id);
    }
}
