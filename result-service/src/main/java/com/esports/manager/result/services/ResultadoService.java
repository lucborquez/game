package com.esports.manager.result.services;


import com.esports.manager.result.exceptions.ResultadoNotFoundException;
import com.esports.manager.result.models.Resultado;
import com.esports.manager.result.models.dto.ResultadoDTO;
import com.esports.manager.result.repositories.ResultadoRepository;
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

    public Resultado crearResultado(ResultadoDTO dto) {
        log.info("creando resultado para partidaId:{} " + dto.getPartidaId());

        if(resultadoRepository.existsByPartidaId(dto.getPartidaId())) {
            throw new RuntimeException("ya existe un resultado para esta partida");
        }

        Resultado resultado = new Resultado();
        resultado.setPartidaId(dto.getPartidaId());
        resultado.setGanadorId(dto.getGanadorId());
        resultado.setPuntajeA(dto.getPuntajeA());
        resultado.setPuntajeB(dto.getPuntajeB());
        resultado.setEstadoValidacion("PENDIENTE");
        resultado.setEstado(true);
        return resultadoRepository.save(resultado);
    }

    public List<Resultado> listarResultados() {
        log.info("listando todos los resultado");
        return resultadoRepository.findAll();
    }

    public Resultado buscarPorId(Long id) {
        log.info("buscando resultado para id:{}", id);
        return resultadoRepository.findById(id).orElseThrow(() -> new ResultadoNotFoundException(id));
    }

    public List<Resultado> listarPorPartida(Long partidaId) {
        log.info("Listando resultados de la partidaId: {}", partidaId);
        return resultadoRepository.findByPartidaId(partidaId);
    }

    public Resultado actualizarResultado(Long id, ResultadoDTO dto) {
        log.info("actualizando resultado para id:{}", id);
        Resultado resultado = buscarPorId(id);

        if(resultado.getEstadoValidacion().equals("VALIDADO")) {
            throw new RuntimeException("el resultado no puede se modificado");
        }

        resultado.setGanadorId(dto.getGanadorId());
        resultado.setPuntajeA(dto.getPuntajeA());
        resultado.setPuntajeB(dto.getPuntajeB());
        return resultadoRepository.save(resultado);
    }
    public Resultado validarResultado(Long id){
        log.info("validando resultado para id:{}", id);
        Resultado resultado = buscarPorId(id);
        resultado.setEstadoValidacion("VALIDADO");
        return resultadoRepository.save(resultado);
    }

    public Resultado anularResultado(Long id) {
        log.info("Anulando resultado con ID: {}", id);
        Resultado resultado = buscarPorId(id);
        resultado.setEstado(false);
        resultado.setEstadoValidacion("ANULADO");
        return resultadoRepository.save(resultado);
    }


}
