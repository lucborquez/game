package com.esports.tournament_service.repository;

import com.esports.tournament_service.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo, Long> {

    // Buscar por juego
    List<Torneo> findByJuegoId(Long juegoId);

    // Buscar por estado (ej: todos los ABIERTOS)
    List<Torneo> findByEstado(String estado);

    // Buscar por juego Y estado
    List<Torneo> findByJuegoIdAndEstado(Long juegoId, String estado);
}