// MiembroEquipoRepository.java
package com.esports.team_service.repository;

import com.esports.team_service.model.MiembroEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MiembroEquipoRepository extends JpaRepository<MiembroEquipo, Long> {
    List<MiembroEquipo> findByEquipoId(Long equipoId);
    // Para verificar que un jugador no esté dos veces en el mismo equipo
    Optional<MiembroEquipo> findByEquipoIdAndUsuarioId(Long equipoId, Long usuarioId);
}