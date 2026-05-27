package com.esports.manager.ranking.exceptions;

// Excepción personalizada cuando no se encuentra un ranking
public class RankingNotFoundException extends RuntimeException {

    // Mensaje de error con el ID del ranking no encontrado
    public RankingNotFoundException(Long id) {
        super("Ranking no encontrado con ID: " + id);
    }
}