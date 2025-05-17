package com.Challenge.Foro.controller;

import com.Challenge.Foro.dto.curso.CursoDTO;
import com.Challenge.Foro.dto.curso.CursoMapper;
import com.Challenge.Foro.model.Curso;
import com.Challenge.Foro.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final CursoMapper cursoMapper;

    @Autowired
    public CursoController(CursoService cursoService, CursoMapper cursoMapper) {
        this.cursoService = cursoService;
        this.cursoMapper = cursoMapper;
    }

    @PostMapping
    public ResponseEntity<CursoDTO.Detalle> crear(
            @RequestBody CursoDTO.Crear dto,
            UriComponentsBuilder uriBuilder) {
        Curso curso = cursoMapper.toEntity(dto);
        curso = cursoService.crearCurso(
            dto.getNombre(),
            dto.getCategoria()
        );
        URI location = uriBuilder.path("/api/v1/cursos/{id}")
                .buildAndExpand(curso.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(cursoMapper.toDetalle(curso));
    }

    @GetMapping
    public ResponseEntity<Page<CursoDTO.Listado>> listar(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(
            cursoService.listarCursos(pageable)
                .map(cursoMapper::toListado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO.Detalle> obtenerPorId(@PathVariable String id) {
        return cursoService.obtenerCursoPorId(id)
                .map(cursoMapper::toDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO.Detalle> actualizar(
            @PathVariable String id,
            @RequestBody CursoDTO.Actualizar dto) {
        return cursoService.obtenerCursoPorId(id)
                .map(curso -> {
                    cursoMapper.updateEntity(curso, dto);
                    return cursoService.actualizarCurso(
                        id,
                        dto.getNombre(),
                        curso.getCategoria()
                    );
                })
                .map(cursoMapper::toDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
} 