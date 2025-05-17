package com.Challenge.Foro.service;

import com.Challenge.Foro.model.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RespuestaService {
    Respuesta crearRespuesta(String mensaje, String usuarioId, String topicoId);
    Page<Respuesta> listarRespuestas(Pageable pageable);
    List<Respuesta> listarRespuestasPorTopico(String topicoId);
    Optional<Respuesta> obtenerRespuestaPorId(String id);
    Respuesta actualizarRespuesta(String id, String mensaje, String autor, String topicoId);
    void eliminarRespuesta(String id);
} 