package com.esports.registration_service.service;

import com.esports.registration_service.client.SancionClient;
import com.esports.registration_service.client.TorneoClient;
import com.esports.registration_service.dto.InscripcionRequestDTO;
import com.esports.registration_service.dto.InscripcionResponseDTO;
import com.esports.registration_service.exception.InscripcionNotFoundException;
import com.esports.registration_service.model.Inscripcion;
import com.esports.registration_service.repository.InscripcionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionService {

    private static final Logger log = LoggerFactory.getLogger(InscripcionService.class);

    private final InscripcionRepository inscripcionRepository;
    private final TorneoClient torneoClient;
    private final SancionClient sancionClient;

    public InscripcionService(InscripcionRepository inscripcionRepository,
                              TorneoClient torneoClient,
                              SancionClient sancionClient) {
        this.inscripcionRepository = inscripcionRepository;
        this.torneoClient = torneoClient;
        this.sancionClient = sancionClient;
    }

    // Crear inscripcion con todas las validaciones de negocio
    public InscripcionResponseDTO crear(InscripcionRequestDTO dto) {

        // Validar que venga equipoId o jugadorId según el tipo
        if ("EQUIPO".equals(dto.getTipoParticipante()) && dto.getEquipoId() == null) {
            throw new RuntimeException("Debe indicar equipoId para tipo EQUIPO");
        }
        if ("JUGADOR".equals(dto.getTipoParticipante()) && dto.getJugadorId() == null) {
            throw new RuntimeException("Debe indicar jugadorId para tipo JUGADOR");
        }

        // Consultar el torneo en tournament-service
        TorneoClient.TorneoInfoDTO torneo = torneoClient.obtenerTorneo(dto.getTorneoId());
        if (torneo == null) {
            throw new RuntimeException("El torneo no existe o no está disponible");
        }

        // Verificar que el torneo esté ABIERTO
        if (!"ABIERTO".equals(torneo.getEstado())) {
            throw new RuntimeException("El torneo no está abierto para inscripciones");
        }

        // Verificar que no se haya superado el cupo
        long inscritos = inscripcionRepository
                .countByTorneoIdAndEstadoNot(dto.getTorneoId(), "CANCELADA");
        if (inscritos >= torneo.getCupoMaximo()) {
            throw new RuntimeException("El torneo ya alcanzó su cupo máximo");
        }

        // Verificar que el participante no esté sancionado
        if ("JUGADOR".equals(dto.getTipoParticipante())) {
            if (sancionClient.tieneSancionActiva(dto.getJugadorId())) {
                throw new RuntimeException(
                        "El jugador tiene una sanción activa y no puede inscribirse");
            }
            // Verificar inscripcion duplicada
            if (inscripcionRepository.existsByTorneoIdAndJugadorId(
                    dto.getTorneoId(), dto.getJugadorId())) {
                throw new RuntimeException("El jugador ya está inscrito en este torneo");
            }
        }

        if ("EQUIPO".equals(dto.getTipoParticipante())) {
            // Verificar inscripcion duplicada de equipo
            if (inscripcionRepository.existsByTorneoIdAndEquipoId(
                    dto.getTorneoId(), dto.getEquipoId())) {
                throw new RuntimeException("El equipo ya está inscrito en este torneo");
            }
        }

        //crear la inscripcion
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setTorneoId(dto.getTorneoId());
        inscripcion.setEquipoId(dto.getEquipoId());
        inscripcion.setJugadorId(dto.getJugadorId());
        inscripcion.setTipoParticipante(dto.getTipoParticipante());
        inscripcion.setEstado("PENDIENTE");
        inscripcion.setFechaInscripcion(LocalDate.now());

        Inscripcion guardada = inscripcionRepository.save(inscripcion);
        log.info("Inscripcion creada ID: {} para torneoId: {}",
                guardada.getId(), guardada.getTorneoId());
        return convertirAResponse(guardada);
    }

    // Listar todas las inscripciones
    public List<InscripcionResponseDTO> listarTodas() {
        return inscripcionRepository.findAll()
                .stream().map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Listar por torneo
    public List<InscripcionResponseDTO> listarPorTorneo(Long torneoId) {
        log.info("Listando inscripciones del torneoId: {}", torneoId);
        return inscripcionRepository.findByTorneoId(torneoId)
                .stream().map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Listar por jugador
    public List<InscripcionResponseDTO> listarPorJugador(Long jugadorId) {
        log.info("Listando inscripciones del jugadorId: {}", jugadorId);
        return inscripcionRepository.findByJugadorId(jugadorId)
                .stream().map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public InscripcionResponseDTO buscarPorId(Long id) {
        Inscripcion i = inscripcionRepository.findById(id)
                .orElseThrow(() -> new InscripcionNotFoundException(id));
        return convertirAResponse(i);
    }

    // Actualizar estado
    public InscripcionResponseDTO actualizarEstado(Long id, String nuevoEstado) {
        Inscripcion i = inscripcionRepository.findById(id)
                .orElseThrow(() -> new InscripcionNotFoundException(id));

        if ("CANCELADA".equals(i.getEstado())) {
            throw new RuntimeException("No se puede modificar una inscripcion cancelada");
        }

        i.setEstado(nuevoEstado);
        Inscripcion actualizada = inscripcionRepository.save(i);
        log.info("Inscripcion ID {} actualizada a estado: {}", id, nuevoEstado);
        return convertirAResponse(actualizada);
    }

    // Cancelar inscripcion
    public InscripcionResponseDTO cancelar(Long id) {
        return actualizarEstado(id, "CANCELADA");
    }

    private InscripcionResponseDTO convertirAResponse(Inscripcion i) {
        InscripcionResponseDTO dto = new InscripcionResponseDTO();
        dto.setId(i.getId());
        dto.setTorneoId(i.getTorneoId());
        dto.setEquipoId(i.getEquipoId());
        dto.setJugadorId(i.getJugadorId());
        dto.setTipoParticipante(i.getTipoParticipante());
        dto.setEstado(i.getEstado());
        dto.setFechaInscripcion(i.getFechaInscripcion());
        return dto;
    }
}