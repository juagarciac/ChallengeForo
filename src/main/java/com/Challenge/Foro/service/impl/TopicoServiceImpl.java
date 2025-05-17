package com.Challenge.Foro.service.impl;

import com.Challenge.Foro.model.Curso;
import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.repository.CursoRepository;
import com.Challenge.Foro.repository.TopicoRepository;
import com.Challenge.Foro.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoServiceImpl implements TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public Topico crearTopico(String titulo, String mensaje, String autor, String cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Topico topico = new Topico(titulo, mensaje, autor, curso);
        // Guardar solo el tópico para evitar referencias circulares
        topico = topicoRepository.save(topico);
        
        return topico;
    }

    @Override
    public Page<Topico> listarTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable);
    }

    @Override
    public Optional<Topico> obtenerTopicoPorId(String id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            // Limitar las respuestas a 10
            Topico t = topico.get();
            if (t.getRespuestas().size() > 10) {
                t.setRespuestas(t.getRespuestas().subList(0, 10));
            }
        }
        return topico;
    }

    @Override
    public Topico actualizarTopico(String id, String titulo, String mensaje, String autor, String cursoId) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Si el curso cambió, actualizar las referencias
        if (!topico.getCurso().getId().equals(cursoId)) {
            // Remover el tópico del curso anterior
            Curso cursoAnterior = topico.getCurso();
            cursoAnterior.getTopicos().remove(topico);
            cursoRepository.save(cursoAnterior);
            
            // Agregar el tópico al nuevo curso
            curso.getTopicos().add(topico);
            cursoRepository.save(curso);
        }

        topico.setTitulo(titulo);
        topico.setMensaje(mensaje);
        topico.setAutor(autor);
        topico.setCurso(curso);

        return topicoRepository.save(topico);
    }

    @Override
    public void eliminarTopico(String id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));
        
        // Remover el tópico de la lista del curso
        Curso curso = topico.getCurso();
        curso.getTopicos().remove(topico);
        cursoRepository.save(curso);
        
        // Eliminar el tópico
        topicoRepository.deleteById(id);
    }
}

