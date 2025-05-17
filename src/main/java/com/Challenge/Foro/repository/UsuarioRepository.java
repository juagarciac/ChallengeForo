package com.Challenge.Foro.repository;

import com.Challenge.Foro.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByNombre(String username);
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
}
