package com.esports.manager.registration.repositories;

import com.esports.manager.registration.models.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    //busca todas las inscripciones de un torneo especifico
    List<Inscripcion> findByTorneoId (Long torneoId);

    //busca todas las inscripciones de un equipo especifico
    List<Inscripcion> findByEquipoId(Long equipoId);

    //busca todas las incripciones de un jugador especifico
    List<Inscripcion> findByJugadorId(Long jugadorId);

    //verifica si ya existe una inscirpcion para ese torneo y equipo
    boolean existsByTorneoIdAndEquipoId (Long torneoId, Long equipoId);

    //verifica si ya existe una inscripcion para ese torneo y jugador
    boolean existsByTorneoIdAndJugadorId (Long torneoId, Long jugadorId);
}
