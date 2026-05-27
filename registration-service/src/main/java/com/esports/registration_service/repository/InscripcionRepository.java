package com.esports.registration_service.repository;

import com.esports.registration_service.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByTorneoId(Long torneoId);
    List<Inscripcion> findByJugadorId(Long jugadorId);
    List<Inscripcion> findByEquipoId(Long equipoId);

    // Para verificar si ya está inscrito en el mismo torneo
    boolean existsByTorneoIdAndJugadorId(Long torneoId, Long jugadorId);
    boolean existsByTorneoIdAndEquipoId(Long torneoId, Long equipoId);

    // Para contar cuántos hay inscritos en un torneo
    long countByTorneoIdAndEstadoNot(Long torneoId, String estado);
}