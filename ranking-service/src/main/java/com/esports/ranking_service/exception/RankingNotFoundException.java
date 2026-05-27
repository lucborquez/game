package com.esports.ranking_service.exception;

public class RankingNotFoundException extends RuntimeException {
    public RankingNotFoundException(Long id) {
        super("Ranking no encontrado con ID: " + id);
    }
}
