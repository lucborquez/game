package com.esports.tournament_service.controller;

import com.esports.tournament_service.dto.*;
import com.esports.tournament_service.service.TorneoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/torneos")
public class TorneoController {

    private final TorneoService torneoService;

    public TorneoController(TorneoService torneoService) {
        this.torneoService = torneoService;
    }

    // POST /api/torneos
    @PostMapping
    public ResponseEntity<TorneoResponseDTO> crear(
            @Valid @RequestBody TorneoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(torneoService.crear(dto));
    }

    // GET /api/torneos
    // GET /api/torneos?estado=ABIERTO
    // GET /api/torneos?juegoId=1
    // GET /api/torneos?juegoId=1&estado=ABIERTO
    @GetMapping
    public ResponseEntity<List<TorneoResponseDTO>> listar(
            @RequestParam(required = false) Long juegoId,
            @RequestParam(required = false) String estado) {
        return ResponseEntity.ok(torneoService.listar(juegoId, estado));
    }

    // GET /api/torneos/1
    @GetMapping("/{id}")
    public ResponseEntity<TorneoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(torneoService.buscarPorId(id));
    }

    // PUT /api/torneos/1
    @PutMapping("/{id}")
    public ResponseEntity<TorneoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TorneoRequestDTO dto) {
        return ResponseEntity.ok(torneoService.actualizar(id, dto));
    }

    // PATCH /api/torneos/1/estado → Cambiar solo el estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TorneoResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody CambioEstadoDTO dto) {
        return ResponseEntity.ok(torneoService.cambiarEstado(id, dto));
    }

    // GET /api/torneos/1/abierto → Para consultas de otros servicios
    @GetMapping("/{id}/abierto")
    public ResponseEntity<Boolean> verificarAbierto(@PathVariable Long id) {
        return ResponseEntity.ok(torneoService.torneoEstaAbierto(id));
    }

    // GET /api/torneos/1/inscripcion → Para que registration-service valide
    @GetMapping("/{id}/inscripcion")
    public ResponseEntity<TorneoResponseDTO> obtenerParaInscripcion(
            @PathVariable Long id) {
        return ResponseEntity.ok(torneoService.obtenerParaInscripcion(id));
    }
}