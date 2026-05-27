package com.esports.manager.ranking.repositories;

import com.esports.manager.ranking.models.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// Repositorio que permite acceder a la base de datos para la entidad Ranking
@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    // Busca todos los rankings de un torneo específico
    List<Ranking> findByTorneoIdOrderByPuntosDesc(Long torneoId);

    // Busca el ranking de un participante en un torneo
    Optional<Ranking> findByTorneoIdAndParticipanteId(Long torneoId, Long participanteId);

    // Verifica si ya existe un ranking para ese participante en ese torneo
    boolean existsByTorneoIdAndParticipanteId(Long torneoId, Long participanteId);
}