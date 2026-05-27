package com.esports.manager.ranking.services;

import com.esports.manager.ranking.exceptions.RankingNotFoundException;
import com.esports.manager.ranking.models.Ranking;
import com.esports.manager.ranking.models.dto.RankingDTO;
import com.esports.manager.ranking.repositories.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

// Capa de lógica de negocio para gestionar rankings
@Service
@RequiredArgsConstructor
public class RankingService {

    private static final Logger log = LoggerFactory.getLogger(RankingService.class);
    private final RankingRepository rankingRepository;

    // Crea un nuevo registro de ranking validando duplicados
    public Ranking crearRanking(RankingDTO dto) {
        log.info("Creando ranking para torneoId: {}", dto.getTorneoId());

        if (rankingRepository.existsByTorneoIdAndParticipanteId(dto.getTorneoId(), dto.getParticipanteId())) {
            throw new RuntimeException("El participante ya tiene ranking en este torneo");
        }

        Ranking ranking = new Ranking();
        ranking.setTorneoId(dto.getTorneoId());
        ranking.setParticipanteId(dto.getParticipanteId());
        ranking.setPuntos(0);
        ranking.setVictorias(0);
        ranking.setDerrotas(0);
        ranking.setDiferencia(0);
        ranking.setPosicion(0);
        return rankingRepository.save(ranking);
    }

    // Retorna el ranking de un torneo ordenado por puntos
    public List<Ranking> listarPorTorneo(Long torneoId) {
        log.info("Listando ranking del torneoId: {}", torneoId);
        return rankingRepository.findByTorneoIdOrderByPuntosDesc(torneoId);
    }

    // Busca el ranking de un participante en un torneo
    public Ranking buscarPorParticipante(Long torneoId, Long participanteId) {
        log.info("Buscando ranking para participanteId: {} en torneoId: {}", participanteId, torneoId);
        return rankingRepository.findByTorneoIdAndParticipanteId(torneoId, participanteId)
                .orElseThrow(() -> new RankingNotFoundException(participanteId));
    }

    // Busca un ranking por su ID
    public Ranking buscarPorId(Long id) {
        log.info("Buscando ranking con ID: {}", id);
        return rankingRepository.findById(id)
                .orElseThrow(() -> new RankingNotFoundException(id));
    }

    // Actualiza puntos y estadísticas del ranking
    public Ranking actualizarEstadisticas(Long id, RankingDTO dto) {
        log.info("Actualizando estadísticas del ranking con ID: {}", id);
        Ranking ranking = buscarPorId(id);
        ranking.setPuntos(dto.getPuntos());
        ranking.setVictorias(dto.getVictorias());
        ranking.setDerrotas(dto.getDerrotas());
        ranking.setDiferencia(dto.getDiferencia());
        ranking.setPosicion(dto.getPosicion());
        return rankingRepository.save(ranking);
    }
    //12
    // Reinicia el ranking de un torneo
    public Ranking reiniciarRanking(Long id) {
        log.info("Reiniciando ranking con ID: {}", id);
        Ranking ranking = buscarPorId(id);
        ranking.setPuntos(0);
        ranking.setVictorias(0);
        ranking.setDerrotas(0);
        ranking.setDiferencia(0);
        ranking.setPosicion(0);
        return rankingRepository.save(ranking);
    }
}