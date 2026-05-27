package com.esports.manager.sanction.services;

import com.esports.manager.sanction.exceptions.SancionNotFoundException;
import com.esports.manager.sanction.models.Sancion;
import com.esports.manager.sanction.models.dto.SancionDTO;
import com.esports.manager.sanction.repositories.SancionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

//capa de logica para gestionar sanciones
@Service
@RequiredArgsConstructor
public class SancionService {

    private static final Logger log = LoggerFactory.getLogger(SancionService.class);
    private final SancionRepository sancionRepository;

    // Crea una nueva sanción en el sistema
    public Sancion crearSancion(SancionDTO dto) {
        log.info("Creando sanción para usuarioId: {}", dto.getUsuarioId());
        Sancion sancion = new Sancion();
        sancion.setUsuarioId(dto.getUsuarioId());
        sancion.setEquipoId(dto.getEquipoId());
        sancion.setMotivo(dto.getMotivo());
        sancion.setFechaInicio(dto.getFechaInicio());
        sancion.setFechaFinal(dto.getFechaFin());
        sancion.setSeveridad(dto.getSeveridad());
        sancion.setEstado(true);
        return sancionRepository.save(sancion);
    }

    // Retorna todas las sanciones registradas
    public List<Sancion> listarSanciones() {
        log.info("Listando todas las sanciones");
        return sancionRepository.findAll();
    }

    // Busca una sanción por su ID
    public Sancion buscarPorId(Long id) {
        log.info("Buscando sanción con ID: {}", id);
        return sancionRepository.findById(id)
                .orElseThrow(() -> new SancionNotFoundException(id));
    }

    // Actualiza los datos de una sanción existente
    public Sancion actualizarSancion(Long id, SancionDTO dto) {
        log.info("Actualizando sanción con ID: {}", id);
        Sancion sancion = buscarPorId(id);
        sancion.setMotivo(dto.getMotivo());
        sancion.setFechaInicio(dto.getFechaInicio());
        sancion.setFechaFinal(dto.getFechaFin());
        sancion.setSeveridad(dto.getSeveridad());
        return sancionRepository.save(sancion);
    }

    // Cierra una sanción marcándola como inactiva
    public Sancion cerrarSancion(Long id) {
        log.info("Cerrando sanción con ID: {}", id);
        Sancion sancion = buscarPorId(id);
        sancion.setEstado(false);
        return sancionRepository.save(sancion);
    }

    // Verifica si un usuario tiene una sanción activa
    public boolean tieneSancionActiva(Long usuarioId) {
        log.info("Verificando sanción activa para usuarioId: {}", usuarioId);
        return sancionRepository.findByUsuarioId(usuarioId)
                .stream()
                .anyMatch(s -> s.getEstado().equals(true));
    }
}
