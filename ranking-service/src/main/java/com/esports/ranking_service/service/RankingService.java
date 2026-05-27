package com.esports.ranking_service.service;

import com.esports.ranking_service.dto.RankingRequestDTO;
import com.esports.ranking_service.dto.RankingResponseDTO;
import com.esports.ranking_service.exception.RankingNotFoundException;
import com.esports.ranking_service.model.Ranking;
import com.esports.ranking_service.repository.RankingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingService {

    private static final Logger log = LoggerFactory.getLogger(RankingService.class);
    private final RankingRepository rankingRepository;

    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    // Crear registro de ranking para un participante en un torneo
    public RankingResponseDTO crear(RankingRequestDTO dto) {
        // Validar que no exista ya un ranking para ese participante en ese torneo
        if (rankingRepository.existsByTorneoIdAndParticipanteId(dto.getTorneoId(), dto.getParticipanteId())) {
            throw new RuntimeException("Ya existe un ranking para el participante "
                    + dto.getParticipanteId() + " en el torneo " + dto.getTorneoId());
        }

        Ranking ranking = new Ranking();
        ranking.setTorneoId(dto.getTorneoId());
        ranking.setParticipanteId(dto.getParticipanteId());
        ranking.setPuntos(dto.getPuntos() != null ? dto.getPuntos() : 0);
        ranking.setVictorias(dto.getVictorias() != null ? dto.getVictorias() : 0);
        ranking.setDerrotas(dto.getDerrotas() != null ? dto.getDerrotas() : 0);
        ranking.setDiferencia(dto.getDiferencia() != null ? dto.getDiferencia() : 0);
        ranking.setPosicion(dto.getPosicion() != null ? dto.getPosicion() : 0);

        Ranking guardado = rankingRepository.save(ranking);
        log.info("Ranking creado para participanteId: {} en torneoId: {}", guardado.getParticipanteId(), guardado.getTorneoId());
        return convertirAResponse(guardado);
    }

    // Listar todos los rankings
    public List<RankingResponseDTO> listarTodos() {
        return rankingRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public RankingResponseDTO buscarPorId(Long id) {
        Ranking ranking = rankingRepository.findById(id)
                .orElseThrow(() -> new RankingNotFoundException(id));
        return convertirAResponse(ranking);
    }

    // Listar rankings de un torneo ordenados por puntos (tabla de posiciones)
    public List<RankingResponseDTO> listarPorTorneo(Long torneoId) {
        log.info("Listando ranking del torneoId: {}", torneoId);
        return rankingRepository.findByTorneoIdOrderByPuntosDesc(torneoId)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Buscar ranking de un participante en un torneo
    public RankingResponseDTO buscarPorTorneoYParticipante(Long torneoId, Long participanteId) {
        Ranking ranking = rankingRepository.findByTorneoIdAndParticipanteId(torneoId, participanteId)
                .orElseThrow(() -> new RankingNotFoundException(participanteId));
        return convertirAResponse(ranking);
    }

    // Listar historial de rankings de un participante
    public List<RankingResponseDTO> listarPorParticipante(Long participanteId) {
        return rankingRepository.findByParticipanteId(participanteId)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Actualizar estadisticas de un ranking (puntos, victorias, derrotas, diferencia, posicion)
    public RankingResponseDTO actualizar(Long id, RankingRequestDTO dto) {
        Ranking ranking = rankingRepository.findById(id)
                .orElseThrow(() -> new RankingNotFoundException(id));

        ranking.setPuntos(dto.getPuntos() != null ? dto.getPuntos() : ranking.getPuntos());
        ranking.setVictorias(dto.getVictorias() != null ? dto.getVictorias() : ranking.getVictorias());
        ranking.setDerrotas(dto.getDerrotas() != null ? dto.getDerrotas() : ranking.getDerrotas());
        ranking.setDiferencia(dto.getDiferencia() != null ? dto.getDiferencia() : ranking.getDiferencia());
        ranking.setPosicion(dto.getPosicion() != null ? dto.getPosicion() : ranking.getPosicion());

        Ranking actualizado = rankingRepository.save(ranking);
        log.info("Ranking actualizado: ID {}", actualizado.getId());
        return convertirAResponse(actualizado);
    }

    // Reiniciar estadisticas de un ranking a cero
    public RankingResponseDTO reiniciar(Long id) {
        Ranking ranking = rankingRepository.findById(id)
                .orElseThrow(() -> new RankingNotFoundException(id));

        ranking.setPuntos(0);
        ranking.setVictorias(0);
        ranking.setDerrotas(0);
        ranking.setDiferencia(0);
        ranking.setPosicion(0);

        Ranking reiniciado = rankingRepository.save(ranking);
        log.info("Ranking reiniciado: ID {}", reiniciado.getId());
        return convertirAResponse(reiniciado);
    }

    // Eliminar ranking
    public void eliminar(Long id) {
        Ranking ranking = rankingRepository.findById(id)
                .orElseThrow(() -> new RankingNotFoundException(id));
        rankingRepository.delete(ranking);
        log.info("Ranking eliminado: ID {}", id);
    }

    private RankingResponseDTO convertirAResponse(Ranking r) {
        RankingResponseDTO dto = new RankingResponseDTO();
        dto.setId(r.getId());
        dto.setTorneoId(r.getTorneoId());
        dto.setParticipanteId(r.getParticipanteId());
        dto.setPuntos(r.getPuntos());
        dto.setVictorias(r.getVictorias());
        dto.setDerrotas(r.getDerrotas());
        dto.setDiferencia(r.getDiferencia());
        dto.setPosicion(r.getPosicion());
        return dto;
    }
}
