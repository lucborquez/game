package com.esports.manager.match.repositories;


import com.esports.manager.match.models.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    //busca TODAS las partidas de un torneo especifico
    List<Partida> findByTorneoId(Long torneoId);

    //busca TODAS las partidas de un torneo especifico
    List<Partida> findByRonda(String ronda);

    //busca TODAS las partidas segun su estado
    List<Partida> findByEstado(Boolean estado);

    //revisa si exsite una partida entre dos participantes en la misma ronda
    boolean existsByTorneoIdAndParticipanteAIdAndParticipanteBIdAndRonda(
            Long torneoId, Long participanteAId, Long participanteBId, String ronda);
}
