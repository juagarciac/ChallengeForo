package com.Challenge.Foro.service.impl;

import com.Challenge.Foro.model.Curso;
import com.Challenge.Foro.repository.CursoRepository;
import com.Challenge.Foro.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public Curso crearCurso(String nombre, String categoria) {
        // Validar que no exista un curso con el mismo nombre
        if (cursoRepository.findByNombre(nombre).isPresent()) {
            throw new RuntimeException("Ya existe un curso con el nombre: " + nombre);
        }

        Curso curso = new Curso(nombre, categoria);
        return cursoRepository.save(curso);
    }

    @Override
    public Page<Curso> listarCursos(Pageable pageable) {
        return cursoRepository.findAll(pageable);
    }

    @Override
    public Optional<Curso> obtenerCursoPorId(String id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso actualizarCurso(String id, String nombre, String categoria) {
        // Validar que el curso exista
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Validar que el nuevo nombre no esté en uso por otro curso (si cambió)
        if (nombre != null && !nombre.isEmpty() && !curso.getNombre().equals(nombre) &&
            cursoRepository.findByNombre(nombre).isPresent()) {
            throw new RuntimeException("Ya existe un curso con el nombre: " + nombre);
        }

        if (nombre != null && !nombre.isEmpty()) {
            curso.setNombre(nombre);
        }
        if (categoria != null && !categoria.isEmpty()) {
            curso.setCategoria(categoria);
        }
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminarCurso(String id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Validar que el curso no tenga tópicos asociados
        if (!curso.getTopicos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar el curso porque tiene tópicos asociados");
        }

        cursoRepository.deleteById(id);
    }
} 