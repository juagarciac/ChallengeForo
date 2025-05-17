package com.Challenge.Foro.service.impl;

import com.Challenge.Foro.model.Respuesta;
import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.model.Usuario;
import com.Challenge.Foro.repository.RespuestaRepository;
import com.Challenge.Foro.repository.TopicoRepository;
import com.Challenge.Foro.repository.UsuarioRepository;
import com.Challenge.Foro.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Respuesta crearRespuesta(String mensaje, String usuarioId, String topicoId) {
        // Validar que el tópico exista
        Topico topico = topicoRepository.findById(topicoId)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        // Validar que el usuario exista
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Respuesta respuesta = new Respuesta();
        respuesta.setMensaje(mensaje);
        respuesta.setTopico(topico);
        respuesta.setAutor(usuario);
        respuesta.setFechaCreacion(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Guardar la respuesta
        respuesta = respuestaRepository.save(respuesta);

        // Actualizar las referencias
        topico.getRespuestas().add(respuesta);
        topicoRepository.save(topico);

        usuario.getRespuestas().add(respuesta);
        usuarioRepository.save(usuario);

        return respuesta;
    }

    @Override
    public Page<Respuesta> listarRespuestas(Pageable pageable) {
        return respuestaRepository.findAll(pageable);
    }

    @Override
    public List<Respuesta> listarRespuestasPorTopico(String topicoId) {
        Topico topico = topicoRepository.findById(topicoId)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));
        return topico.getRespuestas();
    }

    @Override
    public Optional<Respuesta> obtenerRespuestaPorId(String id) {
        return respuestaRepository.findById(id);
    }

    @Override
    public Respuesta actualizarRespuesta(String id, String mensaje, String autorId, String topicoId) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));

        // Validar y actualizar autor si es necesario
        if (autorId != null && !autorId.equals(respuesta.getAutor().getId())) {
            Usuario nuevoAutor = usuarioRepository.findById(autorId)
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado para actualizar respuesta"));
            // Remover de la lista de respuestas del autor anterior
            respuesta.getAutor().getRespuestas().remove(respuesta);
            usuarioRepository.save(respuesta.getAutor());
            // Asignar nuevo autor y agregar a su lista
            respuesta.setAutor(nuevoAutor);
            nuevoAutor.getRespuestas().add(respuesta);
            usuarioRepository.save(nuevoAutor);
        }

        // Validar y actualizar tópico si es necesario
        if (topicoId != null && !topicoId.equals(respuesta.getTopico().getId())) {
            Topico nuevoTopico = topicoRepository.findById(topicoId)
                    .orElseThrow(() -> new RuntimeException("Tópico no encontrado para actualizar respuesta"));
            // Remover de la lista de respuestas del tópico anterior
            respuesta.getTopico().getRespuestas().remove(respuesta);
            topicoRepository.save(respuesta.getTopico());
            // Asignar nuevo tópico y agregar a su lista
            respuesta.setTopico(nuevoTopico);
            nuevoTopico.getRespuestas().add(respuesta);
            topicoRepository.save(nuevoTopico);
        }

        respuesta.setMensaje(mensaje);
        return respuestaRepository.save(respuesta);
    }

    @Override
    public void eliminarRespuesta(String id) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));

        // Remover la respuesta de las referencias
        Topico topico = respuesta.getTopico();
        topico.getRespuestas().remove(respuesta);
        topicoRepository.save(topico);

        Usuario usuario = respuesta.getAutor();
        usuario.getRespuestas().remove(respuesta);
        usuarioRepository.save(usuario);

        // Eliminar la respuesta
        respuestaRepository.deleteById(id);
    }
} 