package com.esports.auth_service.controller;

import com.esports.auth_service.dto.*;
import com.esports.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /api/auth/registro → Crear nueva cuenta
    @PostMapping("/registro")
    public ResponseEntity<CuentaResponseDTO> registrar(
            @Valid @RequestBody CuentaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.registrar(dto));
    }

    // POST /api/auth/login → Iniciar sesión y obtener token
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    // GET /api/auth/cuentas → Listar todas las cuentas
    @GetMapping("/cuentas")
    public ResponseEntity<List<CuentaResponseDTO>> listar(
            @RequestParam(required = false) String rol) {
        if (rol != null) return ResponseEntity.ok(authService.listarPorRol(rol));
        return ResponseEntity.ok(authService.listar());
    }

    // GET /api/auth/cuentas/1 → Buscar cuenta por ID
    @GetMapping("/cuentas/{id}")
    public ResponseEntity<CuentaResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(authService.buscarPorId(id));
    }

    // PUT /api/auth/cuentas/1 → Actualizar cuenta
    @PutMapping("/cuentas/{id}")
    public ResponseEntity<CuentaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CuentaRequestDTO dto) {
        return ResponseEntity.ok(authService.actualizar(id, dto));
    }

    // DELETE /api/auth/cuentas/1 → Desactivar cuenta
    @DeleteMapping("/cuentas/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        authService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}