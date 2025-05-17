package com.Challenge.Foro.controller;

import com.Challenge.Foro.dto.usuario.UsuarioDTO;
import com.Challenge.Foro.dto.usuario.UsuarioMapper;
import com.Challenge.Foro.model.Usuario;
import com.Challenge.Foro.model.Perfil;
import com.Challenge.Foro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO.Detalle> crear(
            @RequestBody UsuarioDTO.Crear dto,
            UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioService.crearUsuario(
            dto.getNombre(),
            dto.getCorreoElectronico(),
            dto.getContrasena(),
            Perfil.valueOf(dto.getPerfil().toUpperCase())
        );
        URI location = uriBuilder.path("/api/v1/usuarios/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(usuarioMapper.toDetalle(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO.Listado>> listar(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(
            usuarioService.listarUsuarios(pageable)
                .map(usuarioMapper::toListado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO.Detalle> obtenerPorId(@PathVariable String id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(usuarioMapper::toDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO.Detalle> actualizar(
            @PathVariable String id,
            @RequestBody UsuarioDTO.Actualizar dto) {
        return usuarioService.obtenerUsuarioPorId(id)
                .<Usuario>map(usuario -> {
                    return usuarioService.actualizarInformacionUsuario(
                        id,
                        dto.getNombre(),
                        Perfil.valueOf(dto.getPerfil().toUpperCase())
                    );
                })
                .map(usuarioMapper::toDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
} 