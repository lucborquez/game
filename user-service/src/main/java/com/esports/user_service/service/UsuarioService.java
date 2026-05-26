package com.esports.user_service.service;


import com.esports.user_service.dto.UsuarioRequestDTO;
import com.esports.user_service.dto.UsuarioResponseDTO;
import com.esports.user_service.exception.UsuarioNotFoundException;
import com.esports.user_service.model.Usuario;
import com.esports.user_service.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        usuarioRepository.findByApodo(dto.getApodo()).ifPresent(u ->{
            throw new RuntimeException("Ya existe un usuario con el Apodo: " + dto.getApodo());
        });
        usuarioRepository.findByEmail(dto.getEmail()).ifPresent(u ->{
            throw new RuntimeException("Ya existe un usuario con ese email: " + dto.getEmail());
        });

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApodo(dto.getApodo());
        usuario.setEmail(dto.getEmail());
        usuario.setRol(dto.getRol());
        usuario.setFechaRegistro(LocalDate.now());

        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario creado: {}  con ID {}", guardado.getApodo(), guardado.getId());
        return convertirAResponse(guardado);
    }

    public List<UsuarioResponseDTO> listarTodo() {
        return usuarioRepository.findAll().stream().map(this::convertirAResponse).collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> listarPorRol(String rol) {
        return usuarioRepository.findByRol(rol).stream().map(this::convertirAResponse).collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        return convertirAResponse(usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id)));
    }

    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));

        usuario.setNombre(dto.getNombre());
        usuario.setApodo(dto.getApodo());
        usuario.setEmail(dto.getEmail());
        usuario.setRol(dto.getRol());

        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Usuario actualizado: ID {}", actualizado.getId());
        return convertirAResponse(actualizado);
    }

    public void desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
        usuario.setEstado(false);
        usuarioRepository.save(usuario);
        log.info("Usuario desactivado: ID {}", usuario.getId());
    }

    public Boolean existeYActivo(Long id) {
        return usuarioRepository.findById(id).map(u -> u.getEstado()).orElse(false);
    }

    private UsuarioResponseDTO convertirAResponse(Usuario u) {
        UsuarioResponseDTO r = new UsuarioResponseDTO();
        r.setId(u.getId());
        r.setNombre(u.getNombre());
        r.setApodo(u.getApodo());
        r.setEmail(u.getEmail());
        r.setRol(u.getRol());
        r.setFechaRegistro(u.getFechaRegistro());
        return r;
    }
}
