package com.esports.manager.registration.controllers;

import com.esports.manager.registration.models.Inscripcion;
import com.esports.manager.registration.models.dto.InscripcionDTO;
import com.esports.manager.registration.services.InscripcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<Inscripcion> crearInscripcion(@Valid @RequestBody InscripcionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionService.crearInscripcion(dto));
    }

    @GetMapping
    public ResponseEntity<List<Inscripcion>> listarInscripciones() {
        return ResponseEntity.ok(inscripcionService.listarInscripciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> buscarporId(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.buscarPorId(id));
    }

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<Inscripcion>> listarPorTorneo(@PathVariable Long torneoId) {
        return ResponseEntity.ok(inscripcionService.listarPorTorneo(torneoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> actualizarInscripcion(@Valid @RequestBody InscripcionDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.actualizarInscripcion(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Inscripcion> cancelarInscripcion(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.cancelarInscripcion(id));
    }
}
