package com.esports.ranking_service.repository;

import com.esports.ranking_service.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    // Obtener ranking de un torneo ordenado por puntos descendente
    List<Ranking> findByTorneoIdOrderByPuntosDesc(Long torneoId);

    // Buscar ranking de un participante en un torneo especifico
    Optional<Ranking> findByTorneoIdAndParticipanteId(Long torneoId, Long participanteId);

    // Verificar si ya existe ranking para ese participante en ese torneo
    boolean existsByTorneoIdAndParticipanteId(Long torneoId, Long participanteId);

    // Listar todos los rankings de un participante (historial)
    List<Ranking> findByParticipanteId(Long participanteId);
}
