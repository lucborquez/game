// EquipoRepository.java
package com.esports.team_service.repository;

import com.esports.team_service.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    List<Equipo> findByEstadoTrue();
    List<Equipo> findByJuegoPrincipalId(Long juegoPrincipalId);
    List<Equipo> findByCapitanId(Long capitanId);
}