package com.esports.sanction_service.repository;

import com.esports.sanction_service.model.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SancionRepository extends JpaRepository<Sancion, Long> {

    // Buscar todas las sanciones de un usuario especifico
    List<Sancion> findByUsuarioId(Long usuarioId);

    // Buscar todas las sanciones de un equipo especifico
    List<Sancion> findByEquipoId(Long equipoId);

    // Buscar sanciones activas de un usuario
    List<Sancion> findByUsuarioIdAndEstadoTrue(Long usuarioId);

    // Buscar sanciones activas de un equipo
    List<Sancion> findByEquipoIdAndEstadoTrue(Long equipoId);

    // Buscar sanciones por estado
    List<Sancion> findByEstado(Boolean estado);

    // Buscar sanciones por severidad
    List<Sancion> findBySeveridad(String severidad);
}
