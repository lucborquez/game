package com.esports.user_service.repository;


import com.esports.user_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{

        Optional<Usuario> findByApodo(String apodo);
        Optional<Usuario> findByEmail(String email);
        List<Usuario> findByRol(String rol);
        List<Usuario> findByEstadoTrue();
}
