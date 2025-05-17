package com.Challenge.Foro.controller;

import com.Challenge.Foro.dto.topico.TopicoDTO;
import com.Challenge.Foro.dto.topico.TopicoMapper;
import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/topicos")
public class TopicoController {

    private final TopicoService topicoService;
    private final TopicoMapper topicoMapper;

    @Autowired
    public TopicoController(TopicoService topicoService, TopicoMapper topicoMapper) {
        this.topicoService = topicoService;
        this.topicoMapper = topicoMapper;
    }

    @PostMapping
    public ResponseEntity<TopicoDTO.Detalle> crear(
            @RequestBody TopicoDTO.Crear dto,
            UriComponentsBuilder uriBuilder) {
        Topico topico = topicoMapper.toEntity(dto);
        topico = topicoService.crearTopico(
            dto.getTitulo(),
            dto.getMensaje(),
            dto.getAutor(),
            dto.getCursoId()
        );
        URI location = uriBuilder.path("/api/v1/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(topicoMapper.toDetalle(topico));
    }

    @GetMapping
    public ResponseEntity<Page<TopicoDTO.Listado>> listar(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(
            topicoService.listarTopicos(pageable)
                .map(topicoMapper::toListado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO.Detalle> obtenerPorId(@PathVariable String id) {
        return topicoService.obtenerTopicoPorId(id)
                .map(topicoMapper::toDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO.Detalle> actualizar(
            @PathVariable String id,
            @RequestBody TopicoDTO.Actualizar dto) {
        return topicoService.obtenerTopicoPorId(id)
                .map(topico -> {
                    topicoMapper.updateEntity(topico, dto);
                    return topicoService.actualizarTopico(
                        id,
                        dto.getTitulo(),
                        dto.getMensaje(),
                        topico.getAutor(),
                        topico.getCurso().getId()
                    );
                })
                .map(topicoMapper::toDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
} 