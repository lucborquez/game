package com.esports.auth_service.repository;

import com.esports.auth_service.model.CuentaAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaAccesoRepository extends JpaRepository<CuentaAcceso, Long> {

    Optional<CuentaAcceso> findByEmail(String email);
    List<CuentaAcceso> findByRol(String rol);
    List<CuentaAcceso> findByEstado(Boolean estado);
}