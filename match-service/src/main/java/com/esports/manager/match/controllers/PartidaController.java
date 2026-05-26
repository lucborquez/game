package com.esports.manager.match.controllers;

import com.esports.manager.match.exceptions.PartidaNotFoundException;
import com.esports.manager.match.models.Partida;
import com.esports.manager.match.models.dto.PartidaDTO;
import com.esports.manager.match.services.PartidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
@RequiredArgsConstructor

public class PartidaController {
    private final PartidaService partidaService;

    @PostMapping
    public ResponseEntity<Partida> crearPartida(@Valid @RequestBody PartidaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.crearPartida(dto));
    }

    // Retorna todas las partidas
    @GetMapping
    public ResponseEntity<List<Partida>> listarPartidas() {
        return ResponseEntity.ok(partidaService.listarPartida());
    }

    // Busca una partida por ID
    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.buscarPorId(id));
    }

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<Partida>> listarPorTorneoId(@PathVariable Long torneoId) {
        return ResponseEntity.ok(partidaService.listarPorTorneo(torneoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partida> actualizarPartida(@PathVariable Long id, @Valid @RequestBody PartidaDTO dto) {
        return ResponseEntity.ok(partidaService.actualizarPartida(id, dto));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Partida> cancelarPartida(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.cancelaraPartida(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Partida> eliminarPartida(@PathVariable Long id) {
        partidaService.eliminarPartida(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(PartidaNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(PartidaNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
