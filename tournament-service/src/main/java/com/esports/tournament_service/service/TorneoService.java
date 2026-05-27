package com.esports.tournament_service.service;

import com.esports.tournament_service.client.JuegoClient;
import com.esports.tournament_service.dto.*;
import com.esports.tournament_service.model.Torneo;
import com.esports.tournament_service.repository.TorneoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TorneoService {

    private static final Logger log = LoggerFactory.getLogger(TorneoService.class);

    private final TorneoRepository torneoRepository;
    private final JuegoClient juegoClient;

    public TorneoService(TorneoRepository torneoRepository,
                         JuegoClient juegoClient) {
        this.torneoRepository = torneoRepository;
        this.juegoClient = juegoClient;
    }

    // Crear torneo
    public TorneoResponseDTO crear(TorneoRequestDTO dto) {
        // Verificar que el juego existe en game-service
        if (!juegoClient.juegoExiste(dto.getJuegoId())) {
            throw new RuntimeException("El juego no existe o está inactivo");
        }

        // Validar que fecha inicio sea posterior al cierre de inscripción
        if (dto.getFechaInicio().compareTo(dto.getFechaCierreInscripcion()) <= 0) {
            throw new RuntimeException(
                    "La fecha de inicio debe ser posterior al cierre de inscripción"
            );
        }

        // Validar que fecha fin sea posterior a fecha inicio
        if (dto.getFechaFin().compareTo(dto.getFechaInicio()) <= 0) {
            throw new RuntimeException(
                    "La fecha de fin debe ser posterior a la fecha de inicio"
            );
        }

        Torneo torneo = new Torneo();
        torneo.setNombre(dto.getNombre());
        torneo.setJuegoId(dto.getJuegoId());
        torneo.setFechaInicio(dto.getFechaInicio());
        torneo.setFechaFin(dto.getFechaFin());
        torneo.setFechaCierreInscripcion(dto.getFechaCierreInscripcion());
        torneo.setCupoMaximo(dto.getCupoMaximo());
        torneo.setModalidad(dto.getModalidad());
        // Siempre empieza en BORRADOR
        torneo.setEstado("BORRADOR");

        Torneo guardado = torneoRepository.save(torneo);
        log.info("Torneo creado: {} con ID: {}", guardado.getNombre(), guardado.getId());
        return convertirAResponse(guardado);
    }

    // Listar todos o filtrar por juego y/o estado
    public List<TorneoResponseDTO> listar(Long juegoId, String estado) {
        List<Torneo> torneos;

        if (juegoId != null && estado != null) {
            torneos = torneoRepository.findByJuegoIdAndEstado(juegoId, estado);
        } else if (juegoId != null) {
            torneos = torneoRepository.findByJuegoId(juegoId);
        } else if (estado != null) {
            torneos = torneoRepository.findByEstado(estado);
        } else {
            torneos = torneoRepository.findAll();
        }

        return torneos.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public TorneoResponseDTO buscarPorId(Long id) {
        Torneo torneo = torneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Torneo no encontrado con ID: " + id));
        return convertirAResponse(torneo);
    }

    // Actualizar fechas, cupos o modalidad
    public TorneoResponseDTO actualizar(Long id, TorneoRequestDTO dto) {
        Torneo torneo = torneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Torneo no encontrado con ID: " + id));

        // No se puede modificar un torneo en curso
        if (torneo.getEstado().equals("EN_CURSO")) {
            throw new RuntimeException(
                    "No se puede modificar un torneo que está en curso"
            );
        }

        torneo.setNombre(dto.getNombre());
        torneo.setJuegoId(dto.getJuegoId());
        torneo.setFechaInicio(dto.getFechaInicio());
        torneo.setFechaFin(dto.getFechaFin());
        torneo.setFechaCierreInscripcion(dto.getFechaCierreInscripcion());
        torneo.setCupoMaximo(dto.getCupoMaximo());
        torneo.setModalidad(dto.getModalidad());

        Torneo actualizado = torneoRepository.save(torneo);
        log.info("Torneo actualizado: ID {}", actualizado.getId());
        return convertirAResponse(actualizado);
    }

    // Cambiar estado del torneo
    public TorneoResponseDTO cambiarEstado(Long id, CambioEstadoDTO dto) {
        Torneo torneo = torneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Torneo no encontrado con ID: " + id));

        // Validar transiciones de estado permitidas
        validarTransicionEstado(torneo.getEstado(), dto.getEstado());

        torneo.setEstado(dto.getEstado());
        Torneo actualizado = torneoRepository.save(torneo);
        log.info("Torneo ID {} cambió estado: {} → {}",
                id, torneo.getEstado(), dto.getEstado());
        return convertirAResponse(actualizado);
    }

    // Verifica que la transición de estado sea válida
    // Ej: no puedes pasar de FINALIZADO a ABIERTO
    private void validarTransicionEstado(String estadoActual, String estadoNuevo) {
        boolean valido = switch (estadoActual) {
            case "BORRADOR"   -> List.of("ABIERTO", "CANCELADO").contains(estadoNuevo);
            case "ABIERTO"    -> List.of("CERRADO", "CANCELADO").contains(estadoNuevo);
            case "CERRADO"    -> List.of("EN_CURSO", "CANCELADO").contains(estadoNuevo);
            case "EN_CURSO"   -> List.of("FINALIZADO", "CANCELADO").contains(estadoNuevo);
            case "FINALIZADO" -> false;
            case "CANCELADO"  -> false;
            default -> false;
        };

        if (!valido) {
            throw new RuntimeException(
                    "Transición de estado inválida: " + estadoActual + " → " + estadoNuevo
            );
        }
    }

    // Para que otros servicios consulten si el torneo está abierto
    public boolean torneoEstaAbierto(Long id) {
        return torneoRepository.findById(id)
                .map(t -> t.getEstado().equals("ABIERTO"))
                .orElse(false);
    }

    // Para que registration-service consulte el cupo máximo
    public TorneoResponseDTO obtenerParaInscripcion(Long id) {
        Torneo torneo = torneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Torneo no encontrado con ID: " + id));

        if (!torneo.getEstado().equals("ABIERTO")) {
            throw new RuntimeException("El torneo no está abierto para inscripciones");
        }
        return convertirAResponse(torneo);
    }

    private TorneoResponseDTO convertirAResponse(Torneo t) {
        TorneoResponseDTO r = new TorneoResponseDTO();
        r.setId(t.getId());
        r.setNombre(t.getNombre());
        r.setJuegoId(t.getJuegoId());
        r.setFechaInicio(t.getFechaInicio());
        r.setFechaFin(t.getFechaFin());
        r.setFechaCierreInscripcion(t.getFechaCierreInscripcion());
        r.setCupoMaximo(t.getCupoMaximo());
        r.setEstado(t.getEstado());
        r.setModalidad(t.getModalidad());
        return r;
    }
}