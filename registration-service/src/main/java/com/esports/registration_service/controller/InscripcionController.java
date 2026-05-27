package com.esports.registration_service.controller;

import com.esports.registration_service.dto.InscripcionRequestDTO;
import com.esports.registration_service.dto.InscripcionResponseDTO;
import com.esports.registration_service.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    // POST → crear inscripcion
    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> crear(
            @Valid @RequestBody InscripcionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inscripcionService.crear(dto));
    }

    // GET → listar todas
    @GetMapping
    public ResponseEntity<List<InscripcionResponseDTO>> listar() {
        return ResponseEntity.ok(inscripcionService.listarTodas());
    }

    // GET → buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.buscarPorId(id));
    }

    // GET → listar por torneo
    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<InscripcionResponseDTO>> porTorneo(
            @PathVariable Long torneoId) {
        return ResponseEntity.ok(inscripcionService.listarPorTorneo(torneoId));
    }

    // GET → listar por jugador
    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<InscripcionResponseDTO>> porJugador(
            @PathVariable Long jugadorId) {
        return ResponseEntity.ok(inscripcionService.listarPorJugador(jugadorId));
    }

    // PUT → actualizar estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<InscripcionResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(inscripcionService.actualizarEstado(id, estado));
    }

    // DELETE → cancelar
    @DeleteMapping("/{id}")
    public ResponseEntity<InscripcionResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.cancelar(id));
    }
}