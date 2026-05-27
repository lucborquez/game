package com.esports.manager.result.repositories;

import com.esports.manager.result.models.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    List<Resultado> findByPartidaId(Long partidaId);

    List<Resultado> findByEstadoValidacion(String estadoValidacion);

    boolean existsByPartidaId(Long partidaId);

}
