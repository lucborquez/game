package com.esports.game_service.service;


import com.esports.game_service.dto.JuegoRequestDTO;
import com.esports.game_service.dto.JuegoResponseDTO;
import com.esports.game_service.exception.JuegoNotFoundException;
import com.esports.game_service.model.Juego;
import com.esports.game_service.repository.JuegoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JuegoService {

    private static final Logger log = LoggerFactory.getLogger(JuegoService.class);
    private final JuegoRepository juegoRepository;

    //Spring inyecta el repositorio automaticamente
    public JuegoService(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    //Crea un juego nuevo
    public JuegoResponseDTO crear(JuegoRequestDTO dto) {
        // Verificar que no exista otro juego con el mismo nombre
        juegoRepository.findByNombre(dto.getNombre()).ifPresent(j -> {
            throw new RuntimeException("Ya existe un juego con el nombre: " + dto.getNombre());
        });

        Juego juego = new Juego();
        juego.setNombre(dto.getNombre());
        juego.setGenero(dto.getGenero());
        juego.setModalidad(dto.getModalidad());
        juego.setJugadoresPorEquipo(dto.getJugadoresPorEquipo());

        Juego guardado = juegoRepository.save(juego);
        log.info("Juego creado: {} con ID: {}", guardado.getNombre(), guardado.getId());
        return convertirAResponse(guardado);
    }

    //Listar juegos activos
    public List<JuegoResponseDTO> listarActivos() {
        return juegoRepository.findByEstadoTrue().stream().map(this::convertirAResponse).collect(Collectors.toList());
    }

    //Buscar juegos por id
    public JuegoResponseDTO buscarPorId(Long id) {
        Juego juego = juegoRepository.findById(id).orElseThrow(() -> new JuegoNotFoundException(id));
        return convertirAResponse(juego);
    }

    //Actualizar modalidad o reglas
    public JuegoResponseDTO actualizar(Long id, JuegoRequestDTO dto) {
        Juego juego = juegoRepository.findById(id).orElseThrow(() -> new JuegoNotFoundException(id));

        juego.setNombre(dto.getNombre());
        juego.setGenero(dto.getGenero());
        juego.setModalidad(dto.getModalidad());
        juego.setJugadoresPorEquipo(dto.getJugadoresPorEquipo());

        Juego actualizado = juegoRepository.save(juego);
        log.info("Juego actualizado: ID {}", actualizado.getId());
        return convertirAResponse(actualizado);
    }

    //Desactivar un juego(pasa a estar inactivo)
    public void desactivar(Long id) {
        Juego juego = juegoRepository.findById(id).orElseThrow(() -> new JuegoNotFoundException(id));
        juego.setEstado(false);
        juegoRepository.save(juego);
        log.info("Juego desactivado: ID {}", id);
    }

    //Convertir entidad a DTO de respuesta
    private JuegoResponseDTO convertirAResponse(Juego juego) {
        JuegoResponseDTO response = new JuegoResponseDTO();
        response.setId(juego.getId());
        response.setNombre(juego.getNombre());
        response.setGenero(juego.getGenero());
        response.setModalidad(juego.getModalidad());
        response.setJugadoresPorEquipo(juego.getJugadoresPorEquipo());
        response.setEstado(juego.isEstado());
        return response;
    }
}