package com.esports.match_service.controller;

import com.esports.match_service.dto.PartidaRequestDTO;
import com.esports.match_service.dto.PartidaResponseDTO;
import com.esports.match_service.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping
    public ResponseEntity<PartidaResponseDTO> crear(
            @Valid @RequestBody PartidaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(partidaService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<PartidaResponseDTO>> listar() {
        return ResponseEntity.ok(partidaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.buscarPorId(id));
    }

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<PartidaResponseDTO>> porTorneo(
            @PathVariable Long torneoId) {
        return ResponseEntity.ok(partidaService.listarPorTorneo(torneoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PartidaRequestDTO dto) {
        return ResponseEntity.ok(partidaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.cancelar(id));
    }
}