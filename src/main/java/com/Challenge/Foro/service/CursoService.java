package com.Challenge.Foro.service;

import com.Challenge.Foro.model.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    Curso crearCurso(String nombre, String categoria);
    Page<Curso> listarCursos(Pageable pageable);
    Optional<Curso> obtenerCursoPorId(String id);
    Curso actualizarCurso(String id, String nombre, String categoria);
    void eliminarCurso(String id);
} 