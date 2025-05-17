package com.Challenge.Foro.service;

import com.Challenge.Foro.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TopicoService {
    Topico crearTopico(String titulo, String mensaje, String autor, String cursoId);
    Page<Topico> listarTopicos(Pageable pageable);
    Optional<Topico> obtenerTopicoPorId(String id);
    Topico actualizarTopico(String id, String titulo, String mensaje, String autor, String cursoId);
    void eliminarTopico(String id);
} 