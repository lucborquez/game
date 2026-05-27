package com.esports.resultservice.controller;

import com.esports.resultservice.model.dto.ResultadoRequestDTO;
import com.esports.resultservice.model.Resultado;
import com.esports.resultservice.service.ResultadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resultados")
@RequiredArgsConstructor
public class ResultadoController {

    private final ResultadoService resultadoService;

    // POST http://localhost:8083/api/resultados (Para crear un resultado)
    @PostMapping
    public ResponseEntity<Resultado> crear(@Valid @RequestBody ResultadoRequestDTO dto) {
        return new ResponseEntity<>(resultadoService.crearResultado(dto), HttpStatus.CREATED);
    }

    // GET http://localhost:8083/api/resultados/partida/{partidaId} (Listar por partida)
    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<List<Resultado>> listarPorPartida(@PathVariable Long partidaId) {
        return ResponseEntity.ok(resultadoService.listarPorPartida(partidaId));
    }

    // GET http://localhost:8083/api/resultados/{id} (Buscar un resultado por su ID)
    @GetMapping("/{id}")
    public ResponseEntity<Resultado> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(resultadoService.buscarPorId(id));
    }

    // PUT http://localhost:8083/api/resultados/{id} (Modificar un resultado)
    @PutMapping("/{id}")
    public ResponseEntity<Resultado> actualizar(@PathVariable Long id, @Valid @RequestBody ResultadoRequestDTO dto) {
        return ResponseEntity.ok(resultadoService.actualizarResultado(id, dto));
    }

    // PATCH http://localhost:8083/api/resultados/{id}/anular?justificacion=... (Anular)
    @PatchMapping("/{id}/anular")
    public ResponseEntity<Resultado> anular(@PathVariable Long id, @RequestParam String justificacion) {
        return ResponseEntity.ok(resultadoService.anularResultado(id, justificacion));
    }

    // PATCH http://localhost:8088/api/resultados/{id}/validar
    @PatchMapping("/{id}/validar")
    public ResponseEntity<Resultado> validar(@PathVariable Long id) {
        return ResponseEntity.ok(resultadoService.validarResultado(id));
    }
}