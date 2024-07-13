package com.Challenge.Foro.repository;

import com.Challenge.Foro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByNombre(String username);
}
