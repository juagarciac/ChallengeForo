package com.Challenge.Foro.repository;

import com.Challenge.Foro.model.Curso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CursoRepository extends MongoRepository<Curso, String> {
    Optional<Curso> findByNombre(String nombre);
} 