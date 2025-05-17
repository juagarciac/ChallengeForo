package com.Challenge.Foro.repository;

import com.Challenge.Foro.model.Respuesta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository extends MongoRepository<Respuesta, String> {
} 