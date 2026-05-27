package com.esports.team_service.controller;

import com.esports.team_service.dto.*;
import com.esports.team_service.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // POST /api/equipos
    @PostMapping
    public ResponseEntity<EquipoResponseDTO> crear(@Valid @RequestBody EquipoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.crear(dto));
    }

    // GET /api/equipos
    @GetMapping
    public ResponseEntity<List<EquipoResponseDTO>> listar() {
        return ResponseEntity.ok(teamService.listarActivos());
    }

    // GET /api/equipos/1
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.buscarPorId(id));
    }

    // PUT /api/equipos/1
    @PutMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EquipoRequestDTO dto) {
        return ResponseEntity.ok(teamService.actualizar(id, dto));
    }

    // POST /api/equipos/1/miembros → Agregar jugador al equipo
    @PostMapping("/{equipoId}/miembros")
    public ResponseEntity<MiembroResponseDTO> agregarMiembro(
            @PathVariable Long equipoId,
            @Valid @RequestBody MiembroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamService.agregarMiembro(equipoId, dto));
    }

    // DELETE /api/equipos/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        teamService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/equipos/1/activo → Para consultas de otros servicios
    @GetMapping("/{id}/activo")
    public ResponseEntity<Boolean> verificarActivo(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.existeYEstaActivo(id));
    }
}