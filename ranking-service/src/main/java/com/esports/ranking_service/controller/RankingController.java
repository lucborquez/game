package com.esports.ranking_service.controller;

import com.esports.ranking_service.dto.RankingRequestDTO;
import com.esports.ranking_service.dto.RankingResponseDTO;
import com.esports.ranking_service.service.RankingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    // POST /api/rankings → Crear nuevo registro de ranking
    @PostMapping
    public ResponseEntity<RankingResponseDTO> crear(@Valid @RequestBody RankingRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rankingService.crear(dto));
    }

    // GET /api/rankings → Listar todos los rankings
    @GetMapping
    public ResponseEntity<List<RankingResponseDTO>> listar() {
        return ResponseEntity.ok(rankingService.listarTodos());
    }

    // GET /api/rankings/{id} → Buscar ranking por ID
    @GetMapping("/{id}")
    public ResponseEntity<RankingResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(rankingService.buscarPorId(id));
    }

    // GET /api/rankings/torneo/{torneoId} → Tabla de posiciones de un torneo
    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<RankingResponseDTO>> listarPorTorneo(@PathVariable Long torneoId) {
        return ResponseEntity.ok(rankingService.listarPorTorneo(torneoId));
    }

    // GET /api/rankings/torneo/{torneoId}/participante/{participanteId}
    @GetMapping("/torneo/{torneoId}/participante/{participanteId}")
    public ResponseEntity<RankingResponseDTO> buscarPorTorneoYParticipante(
            @PathVariable Long torneoId,
            @PathVariable Long participanteId) {
        return ResponseEntity.ok(rankingService.buscarPorTorneoYParticipante(torneoId, participanteId));
    }

    // GET /api/rankings/participante/{participanteId} → Historial de un participante
    @GetMapping("/participante/{participanteId}")
    public ResponseEntity<List<RankingResponseDTO>> listarPorParticipante(@PathVariable Long participanteId) {
        return ResponseEntity.ok(rankingService.listarPorParticipante(participanteId));
    }

    // PUT /api/rankings/{id} → Actualizar estadisticas
    @PutMapping("/{id}")
    public ResponseEntity<RankingResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RankingRequestDTO dto) {
        return ResponseEntity.ok(rankingService.actualizar(id, dto));
    }

    // PATCH /api/rankings/{id}/reiniciar → Reiniciar estadisticas a cero
    @PatchMapping("/{id}/reiniciar")
    public ResponseEntity<RankingResponseDTO> reiniciar(@PathVariable Long id) {
        return ResponseEntity.ok(rankingService.reiniciar(id));
    }

    // DELETE /api/rankings/{id} → Eliminar ranking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rankingService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
