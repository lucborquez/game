package com.esports.team_service.service;

import com.esports.team_service.client.UsuarioClient;
import com.esports.team_service.dto.*;
import com.esports.team_service.model.*;
import com.esports.team_service.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private static final Logger log = LoggerFactory.getLogger(TeamService.class);

    private final EquipoRepository equipoRepository;
    private final MiembroEquipoRepository miembroRepository;
    private final UsuarioClient usuarioClient;

    public TeamService(EquipoRepository equipoRepository,
                       MiembroEquipoRepository miembroRepository,
                       UsuarioClient usuarioClient) {
        this.equipoRepository = equipoRepository;
        this.miembroRepository = miembroRepository;
        this.usuarioClient = usuarioClient;
    }

    // Crear equipo
    public EquipoResponseDTO crear(EquipoRequestDTO dto) {
        // Verificar que el capitán existe y está activo en user-service
        if (!usuarioClient.usuarioExisteYEstaActivo(dto.getCapitanId())) {
            throw new RuntimeException("El capitán no existe o está inactivo");
        }

        Equipo equipo = new Equipo();
        equipo.setNombre(dto.getNombre());
        equipo.setCapitanId(dto.getCapitanId());
        equipo.setJuegoPrincipalId(dto.getJuegoPrincipalId());

        Equipo guardado = equipoRepository.save(equipo);

        // Agregar al capitán como miembro automáticamente
        MiembroEquipo capitan = new MiembroEquipo();
        capitan.setEquipoId(guardado.getId());
        capitan.setUsuarioId(dto.getCapitanId());
        capitan.setRolDentroEquipo("CAPITAN");
        miembroRepository.save(capitan);

        log.info("Equipo creado: {} con ID: {}", guardado.getNombre(), guardado.getId());
        return convertirAResponse(guardado);
    }

    // Listar equipos activos
    public List<EquipoResponseDTO> listarActivos() {
        return equipoRepository.findByEstadoTrue()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public EquipoResponseDTO buscarPorId(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
        return convertirAResponse(equipo);
    }

    // Actualizar equipo
    public EquipoResponseDTO actualizar(Long id, EquipoRequestDTO dto) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));

        equipo.setNombre(dto.getNombre());
        equipo.setCapitanId(dto.getCapitanId());
        equipo.setJuegoPrincipalId(dto.getJuegoPrincipalId());

        Equipo actualizado = equipoRepository.save(equipo);
        log.info("Equipo actualizado: ID {}", actualizado.getId());
        return convertirAResponse(actualizado);
    }

    // Agregar miembro al equipo
    public MiembroResponseDTO agregarMiembro(Long equipoId, MiembroRequestDTO dto) {
        // Verificar que el equipo existe y está activo
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        if (!equipo.getEstado()) {
            throw new RuntimeException("El equipo está inactivo");
        }

        // Verificar que el usuario existe en user-service
        if (!usuarioClient.usuarioExisteYEstaActivo(dto.getUsuarioId())) {
            throw new RuntimeException("El usuario no existe o está inactivo");
        }

        // Verificar que no esté ya en el equipo
        miembroRepository.findByEquipoIdAndUsuarioId(equipoId, dto.getUsuarioId())
                .ifPresent(m -> {
                    throw new RuntimeException("El jugador ya pertenece a este equipo");
                });

        MiembroEquipo miembro = new MiembroEquipo();
        miembro.setEquipoId(equipoId);
        miembro.setUsuarioId(dto.getUsuarioId());
        miembro.setRolDentroEquipo(dto.getRolDentroEquipo());

        MiembroEquipo guardado = miembroRepository.save(miembro);
        log.info("Miembro {} agregado al equipo {}", dto.getUsuarioId(), equipoId);
        return convertirMiembroAResponse(guardado);
    }

    // Desactivar equipo
    public void desactivar(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
        equipo.setEstado(false);
        equipoRepository.save(equipo);
        log.info("Equipo desactivado: ID {}", id);
    }

    // Verificar si equipo existe y está activo (para otros servicios)
    public boolean existeYEstaActivo(Long id) {
        return equipoRepository.findById(id)
                .map(Equipo::getEstado)
                .orElse(false);
    }

    private EquipoResponseDTO convertirAResponse(Equipo equipo) {
        EquipoResponseDTO r = new EquipoResponseDTO();
        r.setId(equipo.getId());
        r.setNombre(equipo.getNombre());
        r.setCapitanId(equipo.getCapitanId());
        r.setJuegoPrincipalId(equipo.getJuegoPrincipalId());
        r.setEstado(equipo.getEstado());
        // Cargar los miembros del equipo
        List<MiembroResponseDTO> miembros = miembroRepository.findByEquipoId(equipo.getId())
                .stream()
                .map(this::convertirMiembroAResponse)
                .collect(Collectors.toList());
        r.setMiembros(miembros);
        return r;
    }

    private MiembroResponseDTO convertirMiembroAResponse(MiembroEquipo m) {
        MiembroResponseDTO r = new MiembroResponseDTO();
        r.setId(m.getId());
        r.setUsuarioId(m.getUsuarioId());
        r.setRolDentroEquipo(m.getRolDentroEquipo());
        return r;
    }
}