package com.esports.game_service.controller;


import com.esports.game_service.dto.JuegoRequestDTO;
import com.esports.game_service.dto.JuegoResponseDTO;
import com.esports.game_service.service.JuegoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Aqui se reciben las peticiones  HTTP
@RestController
@RequestMapping("/api/juegos")
public class JuegoController {

    private final JuegoService juegoService;

    public JuegoController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    //POST -> Crear juegos
    public ResponseEntity<JuegoResponseDTO> crear(@Valid @RequestBody JuegoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoService.crear(dto));
    }

    //GET -> buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.buscarPorId(id));
    }

    //PUT -> actualizar juego
    @PutMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody JuegoRequestDTO dto) {
        return ResponseEntity.ok(juegoService.actualizar(id, dto));
    }

    //DELETE -> Desactivar juego
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        juegoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
//