package com.esports.manager.sanction.repositories;

import com.esports.manager.sanction.models.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio que permite acceder a la base de datos para la entidad Sancion
@Repository
public interface SancionRepository extends JpaRepository<Sancion, Long> {

    // Busca todas las sanciones de un usuario específico
    List<Sancion> findByUsuarioId(Long usuarioId);

    // Busca todas las sanciones de un equipo específico
    List<Sancion> findByEquipoId(Long equipoId);

    // Busca todas las sanciones según su estado
    List<Sancion> findByEstado(Boolean estado);
}
