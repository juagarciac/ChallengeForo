package com.Challenge.Foro.controller;

import com.Challenge.Foro.dto.respuesta.RespuestaDTO;
import com.Challenge.Foro.dto.respuesta.RespuestaMapper;
import com.Challenge.Foro.model.Respuesta;
import com.Challenge.Foro.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RespuestaController {

    private final RespuestaService respuestaService;
    private final RespuestaMapper respuestaMapper;

    @Autowired
    public RespuestaController(RespuestaService respuestaService, RespuestaMapper respuestaMapper) {
        this.respuestaService = respuestaService;
        this.respuestaMapper = respuestaMapper;
    }

    @PostMapping("/respuestas")
    public ResponseEntity<RespuestaDTO.Detalle> crear(
            @RequestBody RespuestaDTO.Crear dto,
            UriComponentsBuilder uriBuilder) {
        Respuesta respuesta = respuestaMapper.toEntity(dto);
        respuesta = respuestaService.crearRespuesta(
            dto.getMensaje(),
            dto.getUsuarioId(),
            dto.getTopicoId()
        );
        URI location = uriBuilder.path("/api/v1/respuestas/{id}")
                .buildAndExpand(respuesta.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(respuestaMapper.toDetalle(respuesta));
    }

    @GetMapping("/respuestas")
    public ResponseEntity<Page<RespuestaDTO.Listado>> listar(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(
            respuestaService.listarRespuestas(pageable)
                .map(respuestaMapper::toListado));
    }

    @GetMapping("/respuestas/{id}")
    public ResponseEntity<RespuestaDTO.Detalle> obtenerPorId(@PathVariable String id) {
        return respuestaService.obtenerRespuestaPorId(id)
                .map(respuestaMapper::toDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/respuestas/{id}")
    public ResponseEntity<RespuestaDTO.Detalle> actualizar(
            @PathVariable String id,
            @RequestBody RespuestaDTO.Actualizar dto) {
        return respuestaService.obtenerRespuestaPorId(id)
                .<Respuesta>map(respuesta -> {
                    respuestaMapper.updateEntity(respuesta, dto);
                    return respuestaService.actualizarRespuesta(
                        id,
                        dto.getMensaje(),
                        respuesta.getAutor().getId(),
                        respuesta.getTopico().getId()
                    );
                })
                .map(respuestaMapper::toDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/respuestas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        respuestaService.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/topicos/{topicoId}/respuestas")
    public ResponseEntity<RespuestaDTO.Detalle> crearPorTopico(
            @PathVariable String topicoId,
            @RequestBody RespuestaDTO.Crear dto,
            UriComponentsBuilder uriBuilder) {
        Respuesta respuesta = respuestaService.crearRespuesta(
            dto.getMensaje(),
            dto.getUsuarioId(),
            topicoId
        );
        URI location = uriBuilder.path("/api/v1/respuestas/{id}")
                .buildAndExpand(respuesta.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(respuestaMapper.toDetalle(respuesta));
    }

    @GetMapping("/topicos/{topicoId}/respuestas")
    public ResponseEntity<List<RespuestaDTO.Listado>> listarPorTopico(@PathVariable String topicoId) {
        List<RespuestaDTO.Listado> list = respuestaService.listarRespuestasPorTopico(topicoId).stream()
                .map(respuestaMapper::toListado)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}

