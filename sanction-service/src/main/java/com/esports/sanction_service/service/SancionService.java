package com.esports.sanction_service.service;

import com.esports.sanction_service.dto.SancionRequestDTO;
import com.esports.sanction_service.dto.SancionResponseDTO;
import com.esports.sanction_service.exception.SancionNotFoundException;
import com.esports.sanction_service.model.Sancion;
import com.esports.sanction_service.repository.SancionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SancionService {

    private static final Logger log = LoggerFactory.getLogger(SancionService.class);
    private final SancionRepository sancionRepository;

    public SancionService(SancionRepository sancionRepository) {
        this.sancionRepository = sancionRepository;
    }

    // Crear una nueva sancion
    public SancionResponseDTO crear(SancionRequestDTO dto) {
        // Debe sancionarse a un usuario o a un equipo, no puede ser ninguno
        if (dto.getUsuarioId() == null && dto.getEquipoId() == null) {
            throw new RuntimeException("Debe indicar un usuarioId o equipoId para aplicar la sancion");
        }

        // Validar que la fecha de fin sea posterior a la fecha de inicio
        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        Sancion sancion = new Sancion();
        sancion.setUsuarioId(dto.getUsuarioId());
        sancion.setEquipoId(dto.getEquipoId());
        sancion.setMotivo(dto.getMotivo());
        sancion.setSeveridad(dto.getSeveridad().toUpperCase());
        sancion.setFechaInicio(dto.getFechaInicio());
        sancion.setFechaFin(dto.getFechaFin());
        sancion.setEstado(true);

        Sancion guardada = sancionRepository.save(sancion);
        log.info("Sancion creada con ID: {} para usuarioId: {} equipoId: {}",
                guardada.getId(), guardada.getUsuarioId(), guardada.getEquipoId());
        return convertirAResponse(guardada);
    }

    // Listar todas las sanciones
    public List<SancionResponseDTO> listarTodas() {
        return sancionRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Buscar sancion por ID
    public SancionResponseDTO buscarPorId(Long id) {
        Sancion sancion = sancionRepository.findById(id)
                .orElseThrow(() -> new SancionNotFoundException(id));
        return convertirAResponse(sancion);
    }

    // Listar sanciones de un usuario
    public List<SancionResponseDTO> listarPorUsuario(Long usuarioId) {
        log.info("Listando sanciones del usuarioId: {}", usuarioId);
        return sancionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Listar sanciones de un equipo
    public List<SancionResponseDTO> listarPorEquipo(Long equipoId) {
        log.info("Listando sanciones del equipoId: {}", equipoId);
        return sancionRepository.findByEquipoId(equipoId)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Verificar si un usuario tiene sancion activa (para otros microservicios)
    public Boolean tieneSancionActiva(Long usuarioId) {
        log.info("Verificando sancion activa para usuarioId: {}", usuarioId);
        return !sancionRepository.findByUsuarioIdAndEstadoTrue(usuarioId).isEmpty();
    }

    // Actualizar motivo, severidad o fechas de una sancion
    public SancionResponseDTO actualizar(Long id, SancionRequestDTO dto) {
        Sancion sancion = sancionRepository.findById(id)
                .orElseThrow(() -> new SancionNotFoundException(id));

        if (!sancion.getEstado()) {
            throw new RuntimeException("No se puede modificar una sancion cerrada");
        }

        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        sancion.setMotivo(dto.getMotivo());
        sancion.setSeveridad(dto.getSeveridad().toUpperCase());
        sancion.setFechaInicio(dto.getFechaInicio());
        sancion.setFechaFin(dto.getFechaFin());

        Sancion actualizada = sancionRepository.save(sancion);
        log.info("Sancion actualizada: ID {}", actualizada.getId());
        return convertirAResponse(actualizada);
    }

    // Cerrar una sancion (marcarla como inactiva)
    public SancionResponseDTO cerrar(Long id) {
        Sancion sancion = sancionRepository.findById(id)
                .orElseThrow(() -> new SancionNotFoundException(id));

        if (!sancion.getEstado()) {
            throw new RuntimeException("La sancion ya esta cerrada");
        }

        sancion.setEstado(false);
        Sancion cerrada = sancionRepository.save(sancion);
        log.info("Sancion cerrada: ID {}", cerrada.getId());
        return convertirAResponse(cerrada);
    }

    // Eliminar una sancion
    public void eliminar(Long id) {
        Sancion sancion = sancionRepository.findById(id)
                .orElseThrow(() -> new SancionNotFoundException(id));
        sancionRepository.delete(sancion);
        log.info("Sancion eliminada: ID {}", id);
    }

    private SancionResponseDTO convertirAResponse(Sancion s) {
        SancionResponseDTO dto = new SancionResponseDTO();
        dto.setId(s.getId());
        dto.setUsuarioId(s.getUsuarioId());
        dto.setEquipoId(s.getEquipoId());
        dto.setMotivo(s.getMotivo());
        dto.setSeveridad(s.getSeveridad());
        dto.setFechaInicio(s.getFechaInicio());
        dto.setFechaFin(s.getFechaFin());
        dto.setEstado(s.getEstado());
        return dto;
    }
}
