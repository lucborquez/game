package com.esports.sanction_service.controller;

import com.esports.sanction_service.dto.SancionRequestDTO;
import com.esports.sanction_service.dto.SancionResponseDTO;
import com.esports.sanction_service.service.SancionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanciones")
public class SancionController {

    private final SancionService sancionService;

    public SancionController(SancionService sancionService) {
        this.sancionService = sancionService;
    }

    // POST /api/sanciones → Crear nueva sancion
    @PostMapping
    public ResponseEntity<SancionResponseDTO> crear(@Valid @RequestBody SancionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sancionService.crear(dto));
    }

    // GET /api/sanciones → Listar todas las sanciones
    @GetMapping
    public ResponseEntity<List<SancionResponseDTO>> listar() {
        return ResponseEntity.ok(sancionService.listarTodas());
    }

    // GET /api/sanciones/{id} → Buscar sancion por ID
    @GetMapping("/{id}")
    public ResponseEntity<SancionResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(sancionService.buscarPorId(id));
    }

    // GET /api/sanciones/usuario/{usuarioId} → Sanciones de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<SancionResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(sancionService.listarPorUsuario(usuarioId));
    }

    // GET /api/sanciones/equipo/{equipoId} → Sanciones de un equipo
    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<SancionResponseDTO>> listarPorEquipo(@PathVariable Long equipoId) {
        return ResponseEntity.ok(sancionService.listarPorEquipo(equipoId));
    }

    // GET /api/sanciones/usuario/{usuarioId}/activa → Para consultas de otros servicios
    @GetMapping("/usuario/{usuarioId}/activa")
    public ResponseEntity<Boolean> tieneSancionActiva(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(sancionService.tieneSancionActiva(usuarioId));
    }

    // PUT /api/sanciones/{id} → Actualizar sancion
    @PutMapping("/{id}")
    public ResponseEntity<SancionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SancionRequestDTO dto) {
        return ResponseEntity.ok(sancionService.actualizar(id, dto));
    }

    // PATCH /api/sanciones/{id}/cerrar → Cerrar sancion
    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<SancionResponseDTO> cerrar(@PathVariable Long id) {
        return ResponseEntity.ok(sancionService.cerrar(id));
    }

    // DELETE /api/sanciones/{id} → Eliminar sancion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        sancionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
