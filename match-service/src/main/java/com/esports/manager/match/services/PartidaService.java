package com.esports.manager.match.services;

import com.esports.manager.match.models.dto.PartidaDTO;
import com.esports.manager.match.exceptions.PartidaNotFoundException;
import com.esports.manager.match.models.Partida;
import com.esports.manager.match.repositories.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

// La capa de 'logica' del negocio para gestionar partidas
@Service
@RequiredArgsConstructor

public class PartidaService {

    private static final Logger log = LoggerFactory.getLogger(PartidaService.class);
    private final PartidaRepository partidaRepository;

    public Partida crearPartida(PartidaDTO dto) {
        log.info("Creando partida para torneoId: {}", dto.getTorneoId());

        if (partidaRepository.existsByTorneoIdAndParticipanteAIdAndParticipanteBIdAndRonda(
                dto.getTorneoId(), dto.getParticipanteAId(), dto.getParticipanteBId(), dto.getRonda())) {
            throw new RuntimeException("Ya existe una partida entre estos participantes en esta ronda");
        }

        Partida partida = new Partida();
        partida.setTorneoId(dto.getTorneoId());
        partida.setParticipanteAId(dto.getParticipanteAId());
        partida.setParticipanteBId(dto.getParticipanteBId());
        partida.setRonda(dto.getRonda());
        partida.setFecha(dto.getFecha());
        partida.setEstado(true);
        return partidaRepository.save(partida);
    }

    public List<Partida> listarPartida() {
        log.info("Listando todos los partidas");
        return partidaRepository.findAll();
    }

    public Partida buscarPorId(Long id) {
        log.info("Buscando partida por id: {}", id);
        return partidaRepository.findById(id).orElseThrow(() -> new PartidaNotFoundException(id));
    }

    public List<Partida> listarPorTorneo(Long torneoId) {
        log.info("listando partidas del torneoId: {}",torneoId);
        return partidaRepository.findByTorneoId(torneoId);
    }

    public Partida actualizarPartida(Long id, PartidaDTO dto) {
        log.info("Actualizando partida con ID: {}", id);
        Partida partida = buscarPorId(id);
        partida.setRonda(dto.getRonda());
        partida.setFecha(dto.getFecha());
        partida.setParticipanteAId(dto.getParticipanteAId());
        partida.setParticipanteBId(dto.getParticipanteBId());
        return partidaRepository.save(partida);
    }

    public Partida cancelaraPartida(Long id) {
        log.info("Cancelando partida con ID: {}", id);
        Partida partida = buscarPorId(id);
        partida.setEstado(false);
        return partidaRepository.save(partida);
    }

    public void eliminarPartida(Long id) {
        log.info("Eliminando partida con ID: {}", id);
        Partida partida = buscarPorId(id);
        partidaRepository.delete(partida);
    }
}
