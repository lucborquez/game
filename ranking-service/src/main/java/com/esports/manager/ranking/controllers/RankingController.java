package com.esports.manager.ranking.controllers;

import com.esports.manager.ranking.exceptions.RankingNotFoundException;
import com.esports.manager.ranking.models.Ranking;
import com.esports.manager.ranking.models.dto.RankingDTO;
import com.esports.manager.ranking.services.RankingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST que expone los endpoints de ranking
@RestController
@RequestMapping("/api/rankings")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    // Crea un nuevo registro de ranking
    @PostMapping
    public ResponseEntity<Ranking> crearRanking(@Valid @RequestBody RankingDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rankingService.crearRanking(dto));
    }

    // Retorna el ranking de un torneo ordenado por puntos
    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<Ranking>> listarPorTorneo(@PathVariable Long torneoId) {
        return ResponseEntity.ok(rankingService.listarPorTorneo(torneoId));
    }

    // Busca el ranking de un participante en un torneo
    @GetMapping("/torneo/{torneoId}/participante/{participanteId}")
    public ResponseEntity<Ranking> buscarPorParticipante(@PathVariable Long torneoId, @PathVariable Long participanteId) {
        return ResponseEntity.ok(rankingService.buscarPorParticipante(torneoId, participanteId));
    }

    // Busca un ranking por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ranking> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rankingService.buscarPorId(id));
    }

    // Actualiza puntos y estadísticas del ranking
    @PutMapping("/{id}")
    public ResponseEntity<Ranking> actualizarEstadisticas(@PathVariable Long id, @Valid @RequestBody RankingDTO dto) {
        return ResponseEntity.ok(rankingService.actualizarEstadisticas(id, dto));
    }

    // Reinicia el ranking de un participante
    @PatchMapping("/{id}/reiniciar")
    public ResponseEntity<Ranking> reiniciarRanking(@PathVariable Long id) {
        return ResponseEntity.ok(rankingService.reiniciarRanking(id));
    }

    // Maneja el error cuando no se encuentra un ranking
    @ExceptionHandler(RankingNotFoundException.class)
    public ResponseEntity<String> handleNotFound(RankingNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}