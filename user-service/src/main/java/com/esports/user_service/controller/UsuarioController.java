package com.esports.user_service.controller;


import com.esports.user_service.dto.UsuarioRequestDTO;
import com.esports.user_service.dto.UsuarioResponseDTO;
import com.esports.user_service.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //POST
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(dto));
    }

    //GET
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar(@RequestParam(required = false)String rol) {
        if (rol != null) return ResponseEntity.ok(usuarioService.listarPorRol(rol));
        return ResponseEntity.ok(usuarioService.listarTodo());
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        usuarioService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    //GET -> para que otros servicios consulten
    @GetMapping("/{id}/activo")
    public ResponseEntity<Boolean> verificarActivo(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.existeYActivo(id));
    }
}
