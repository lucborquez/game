package com.esports.manager.sanction.controllers;


import com.esports.manager.sanction.exceptions.SancionNotFoundException;
import com.esports.manager.sanction.models.Sancion;
import com.esports.manager.sanction.models.dto.SancionDTO;
import com.esports.manager.sanction.services.SancionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanciones")
@RequiredArgsConstructor
public class SancionController {
    private final SancionService sancionService;

    //crea una sancion
    @PostMapping
    public ResponseEntity<?> crearSancion(@Valid @RequestBody SancionDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(sancionService.crearSancion(dto));
    }

    //retorna todas las sanciones
    @GetMapping
    public ResponseEntity<List<Sancion>> listarSanciones(){
        return ResponseEntity.ok(sancionService.listarSanciones());
    }
    //BUSCA UNA SANCION POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Sancion> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(sancionService.buscarPorId(id));
    }
    // Actualiza una sanción existente
    @PutMapping("/{id}")
    public ResponseEntity<Sancion> actualizarSancion(@PathVariable Long id, @Valid @RequestBody SancionDTO dto) {
        return ResponseEntity.ok(sancionService.actualizarSancion(id, dto));
    }

    // Cierra una sanción marcándola como inactiva
    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<Sancion> cerrarSancion(@PathVariable Long id) {
        return ResponseEntity.ok(sancionService.cerrarSancion(id));
    }

    // Verifica si un usuario tiene sanción activa
    @GetMapping("/activa/{usuarioId}")
    public ResponseEntity<Boolean> tieneSancionActiva(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(sancionService.tieneSancionActiva(usuarioId));
    }

    // Maneja el error cuando no se encuentra una sanción
    @ExceptionHandler(SancionNotFoundException.class)
    public ResponseEntity<String> handleNotFound(SancionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
