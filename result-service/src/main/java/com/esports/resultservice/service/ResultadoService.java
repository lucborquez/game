package com.esports.resultservice.service;

import com.esports.resultservice.model.dto.ResultadoRequestDTO;
import com.esports.resultservice.model.Resultado;
import com.esports.resultservice.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultadoService {

    private final ResultadoRepository resultadoRepository;

    public Resultado crearResultado(ResultadoRequestDTO dto) {
        Resultado resultado = new Resultado();
        resultado.setPartidaId(dto.getPartidaId());
        resultado.setGanadorId(dto.getGanadorId());
        resultado.setPuntajeA(dto.getPuntajeA());
        resultado.setPuntajeB(dto.getPuntajeB());
        resultado.setEvidenciaUrl(dto.getEvidenciaUrl());

        return resultadoRepository.save(resultado);
    }

    public List<Resultado> listarPorPartida(Long partidaId) {
        return resultadoRepository.findByPartidaId(partidaId);
    }

    public Resultado buscarPorId(Long id) {
        return resultadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resultado no encontrado con ID: " + id));
    }

    public Resultado actualizarResultado(Long id, ResultadoRequestDTO dto) {
        Resultado existente = buscarPorId(id);

        if ("VALIDADO".equalsIgnoreCase(existente.getEstadoValidacion())) {
            throw new IllegalStateException("Un resultado con validación completa no puede ser alterado.");
        }

        existente.setGanadorId(dto.getGanadorId());
        existente.setPuntajeA(dto.getPuntajeA());
        existente.setPuntajeB(dto.getPuntajeB());
        existente.setEvidenciaUrl(dto.getEvidenciaUrl());

        return resultadoRepository.save(existente);
    }

    public Resultado anularResultado(Long id, String justificacion) {
        Resultado existente = buscarPorId(id);

        existente.setEstadoValidacion("ANULADO");
        existente.setJustificacionAnulacion(justificacion);

        return resultadoRepository.save(existente);
    }
}