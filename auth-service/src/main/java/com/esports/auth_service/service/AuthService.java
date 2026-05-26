package com.esports.auth_service.service;

import com.esports.auth_service.dto.*;
import com.esports.auth_service.model.CuentaAcceso;
import com.esports.auth_service.repository.CuentaAccesoRepository;
import com.esports.auth_service.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final CuentaAccesoRepository cuentaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(CuentaAccesoRepository cuentaRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.cuentaRepository = cuentaRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Registrar nueva cuenta
    public CuentaResponseDTO registrar(CuentaRequestDTO dto) {
        // Verificar que el email no esté en uso
        cuentaRepository.findByEmail(dto.getEmail()).ifPresent(c -> {
            throw new RuntimeException("Ya existe una cuenta con el email: " + dto.getEmail());
        });

        CuentaAcceso cuenta = new CuentaAcceso();
        cuenta.setEmail(dto.getEmail());
        // Encriptamos la contraseña antes de guardar
        cuenta.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        cuenta.setRol(dto.getRol());
        cuenta.setFechaCreacion(LocalDate.parse(LocalDate.now().toString()));

        CuentaAcceso guardada = cuentaRepository.save(cuenta);
        log.info("Cuenta registrada: {} con rol: {}", guardada.getEmail(), guardada.getRol());
        return convertirAResponse(guardada);
    }

    // Login - verifica credenciales y genera token
    public LoginResponseDTO login(LoginRequestDTO dto) {
        // Buscar la cuenta por email
        CuentaAcceso cuenta = cuentaRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        // Verificar que la cuenta esté activa
        if (!cuenta.getEstado()) {
            throw new RuntimeException("La cuenta está desactivada");
        }

        // Comparar la contraseña ingresada con la encriptada en BD
        if (!passwordEncoder.matches(dto.getPassword(), cuenta.getPasswordHash())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // Generar el token JWT
        String token = jwtUtil.generarToken(cuenta.getEmail(), cuenta.getRol());
        log.info("Login exitoso: {}", cuenta.getEmail());

        return new LoginResponseDTO(token, cuenta.getEmail(), cuenta.getRol());
    }

    // Listar cuentas
    public List<CuentaResponseDTO> listar() {
        return cuentaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public List<CuentaResponseDTO> listarPorRol(String rol) {
        return cuentaRepository.findByRol(rol)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public CuentaResponseDTO buscarPorId(Long id) {
        CuentaAcceso cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
        return convertirAResponse(cuenta);
    }

    // Actualizar rol o contraseña
    public CuentaResponseDTO actualizar(Long id, CuentaRequestDTO dto) {
        CuentaAcceso cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        cuenta.setEmail(dto.getEmail());
        cuenta.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        cuenta.setRol(dto.getRol());

        CuentaAcceso actualizada = cuentaRepository.save(cuenta);
        log.info("Cuenta actualizada: ID {}", actualizada.getId());
        return convertirAResponse(actualizada);
    }

    // Desactivar cuenta
    public void desactivar(Long id) {
        CuentaAcceso cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
        cuenta.setEstado(false);
        cuentaRepository.save(cuenta);
        log.info("Cuenta desactivada: ID {}", id);
    }

    private CuentaResponseDTO convertirAResponse(CuentaAcceso c) {
        CuentaResponseDTO r = new CuentaResponseDTO();
        r.setId(c.getId());
        r.setEmail(c.getEmail());
        r.setRol(c.getRol());
        r.setEstado(c.getEstado());
        r.setFechaCreacion(c.getFechaCreacion());
        return r;
    }
}