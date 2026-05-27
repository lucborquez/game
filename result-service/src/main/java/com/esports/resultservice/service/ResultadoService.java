package com.esports.resultservice.service;

import com.esports.resultservice.client.MatchClient;
import com.esports.resultservice.model.dto.ResultadoRequestDTO;
import com.esports.resultservice.model.Resultado;
import com.esports.resultservice.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultadoService {

    private static final Logger log = LoggerFactory.getLogger(ResultadoService.class);

    private final ResultadoRepository resultadoRepository;
    private final MatchClient matchClient;

    // Crear resultado validando que la partida exista y no haya duplicado
    public Resultado crearResultado(ResultadoRequestDTO dto) {

        // Verificar que la partida existe en match-service
        if (!matchClient.verificarPartidaExiste(dto.getPartidaId())) {
            throw new IllegalArgumentException(
                    "La partida con ID " + dto.getPartidaId() + " no existe");
        }

        // No registrar resultado duplicado para la misma partida
        if (!resultadoRepository.findByPartidaId(dto.getPartidaId()).isEmpty()) {
            throw new IllegalArgumentException(
                    "Ya existe un resultado para la partida ID: " + dto.getPartidaId());
        }

        Resultado resultado = new Resultado();
        resultado.setPartidaId(dto.getPartidaId());
        resultado.setGanadorId(dto.getGanadorId());
        resultado.setPuntajeA(dto.getPuntajeA());
        resultado.setPuntajeB(dto.getPuntajeB());
        resultado.setEvidenciaUrl(dto.getEvidenciaUrl());

        Resultado guardado = resultadoRepository.save(resultado);
        log.info("Resultado creado ID: {} para partidaId: {}",
                guardado.getId(), guardado.getPartidaId());
        return guardado;
    }

    // Listar resultados de una partida específica
    public List<Resultado> listarPorPartida(Long partidaId) {
        log.info("Listando resultados para partidaId: {}", partidaId);
        return resultadoRepository.findByPartidaId(partidaId);
    }

    // Listar todos los resultados
    public List<Resultado> listarTodos() {
        return resultadoRepository.findAll();
    }

    // Buscar resultado por ID
    public Resultado buscarPorId(Long id) {
        return resultadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Resultado no encontrado con ID: " + id));
    }

    // Actualizar resultado (solo si no está validado)
    public Resultado actualizarResultado(Long id, ResultadoRequestDTO dto) {
        Resultado existente = buscarPorId(id);

        if ("VALIDADO".equalsIgnoreCase(existente.getEstadoValidacion())) {
            throw new IllegalStateException(
                    "Un resultado con validación completa no puede ser alterado.");
        }

        existente.setGanadorId(dto.getGanadorId());
        existente.setPuntajeA(dto.getPuntajeA());
        existente.setPuntajeB(dto.getPuntajeB());
        existente.setEvidenciaUrl(dto.getEvidenciaUrl());

        Resultado actualizado = resultadoRepository.save(existente);
        log.info("Resultado actualizado ID: {}", actualizado.getId());
        return actualizado;
    }

    // Validar resultado (solo organizador puede hacerlo)
    public Resultado validarResultado(Long id) {
        Resultado existente = buscarPorId(id);

        if (!"PENDIENTE".equalsIgnoreCase(existente.getEstadoValidacion())) {
            throw new IllegalStateException(
                    "Solo se pueden validar resultados en estado PENDIENTE.");
        }

        existente.setEstadoValidacion("VALIDADO");
        Resultado validado = resultadoRepository.save(existente);
        log.info("Resultado validado ID: {}", validado.getId());
        return validado;
    }

    // Anular resultado con justificación
    public Resultado anularResultado(Long id, String justificacion) {
        Resultado existente = buscarPorId(id);

        if ("ANULADO".equalsIgnoreCase(existente.getEstadoValidacion())) {
            throw new IllegalStateException(
                    "El resultado ya está anulado.");
        }

        existente.setEstadoValidacion("ANULADO");
        existente.setJustificacionAnulacion(justificacion);

        Resultado anulado = resultadoRepository.save(existente);
        log.info("Resultado anulado ID: {} — justificacion: {}", id, justificacion);
        return anulado;
    }
}