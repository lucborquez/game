package com.esports.manager.registration.services;

import com.esports.manager.registration.exceptions.InscripcionNotFoundException;
import com.esports.manager.registration.models.Inscripcion;
import com.esports.manager.registration.models.dto.InscripcionDTO;
import com.esports.manager.registration.repositories.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

// Capa de lógica de negocio para gestionar inscripciones
@Service
@RequiredArgsConstructor
public class InscripcionService {

    private static final Logger log = LoggerFactory.getLogger(InscripcionService.class);
    private final InscripcionRepository inscripcionRepository;

    // Crea una nueva inscripción validando duplicados
    public Inscripcion crearInscripcion(InscripcionDTO dto) {
        log.info("Creando inscripción para torneoId: {}", dto.getTorneo());

        // Validar duplicado por equipo
        if (dto.getEquipoId() != null &&
                inscripcionRepository.existsByTorneoIdAndEquipoId(dto.getTorneo(), dto.getEquipoId())) {
            throw new RuntimeException("El equipo ya está inscrito en este torneo");
        }

        // Validar duplicado por jugador
        if (dto.getJugadorId() != null &&
                inscripcionRepository.existsByTorneoIdAndJugadorId(dto.getTorneo(), dto.getJugadorId())) {
            throw new RuntimeException("El jugador ya está inscrito en este torneo");
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setTorneoId(dto.getTorneo());
        inscripcion.setEquipoId(dto.getEquipoId());
        inscripcion.setJugadorId(dto.getJugadorId());
        inscripcion.setTipoParticipante(dto.getTipoParticipante());
        inscripcion.setEstado(true);
        return inscripcionRepository.save(inscripcion);
    }

    // Retorna todas las inscripciones registradas
    public List<Inscripcion> listarInscripciones() {
        log.info("Listando todas las inscripciones");
        return inscripcionRepository.findAll();
    }

    // Busca una inscripción por su ID
    public Inscripcion buscarPorId(Long id) {
        log.info("Buscando inscripción con ID: {}", id);
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new InscripcionNotFoundException(id));
    }

    // Retorna todas las inscripciones de un torneo
    public List<Inscripcion> listarPorTorneo(Long torneoId) {
        log.info("Listando inscripciones del torneoId: {}", torneoId);
        return inscripcionRepository.findByTorneoId(torneoId);
    }

    // Actualiza el estado de una inscripción
    public Inscripcion actualizarInscripcion(Long id, InscripcionDTO dto) {
        log.info("Actualizando inscripción con ID: {}", id);
        Inscripcion inscripcion = buscarPorId(id);
        inscripcion.setTipoParticipante(dto.getTipoParticipante());
        return inscripcionRepository.save(inscripcion);
    }

    // Cancela una inscripción marcándola como inactiva
    public Inscripcion cancelarInscripcion(Long id) {
        log.info("Cancelando inscripción con ID: {}", id);
        Inscripcion inscripcion = buscarPorId(id);
        inscripcion.setEstado(false);
        return inscripcionRepository.save(inscripcion);
    }
}