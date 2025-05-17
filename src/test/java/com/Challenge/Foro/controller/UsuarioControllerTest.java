package com.Challenge.Foro.controller;

import com.Challenge.Foro.dto.usuario.UsuarioDTO;
import com.Challenge.Foro.dto.usuario.UsuarioMapper;
import com.Challenge.Foro.model.Usuario;
import com.Challenge.Foro.model.Perfil;
import com.Challenge.Foro.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean // Mockear el mapper también ya que es usado por el controlador
    private UsuarioMapper usuarioMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;
    private UsuarioDTO.Crear usuarioCrearDTO;
    private UsuarioDTO.Detalle usuarioDetalleDTO;
    private UsuarioDTO.Listado usuarioListadoDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("Test User");
        usuario.setCorreoElectronico("test@test.com");
        usuario.setContrasena("password123");
        usuario.setPerfil(Perfil.ESTUDIANTE);

        usuarioCrearDTO = new UsuarioDTO.Crear();
        usuarioCrearDTO.setNombre("Test User");
        usuarioCrearDTO.setCorreoElectronico("test@test.com");
        usuarioCrearDTO.setContrasena("password123");
        usuarioCrearDTO.setPerfil("ESTUDIANTE"); // Perfil como String en DTO

        usuarioDetalleDTO = new UsuarioDTO.Detalle();
        usuarioDetalleDTO.setId("1");
        usuarioDetalleDTO.setNombre("Test User");
        usuarioDetalleDTO.setCorreoElectronico("test@test.com");
        usuarioDetalleDTO.setPerfil("ESTUDIANTE");
        
        usuarioListadoDTO = new UsuarioDTO.Listado();
        usuarioListadoDTO.setId("1");
        usuarioListadoDTO.setNombre("Test User");
        usuarioListadoDTO.setPerfil("ESTUDIANTE");
    }

    @Test
    public void testCrearUsuario() throws Exception {
        // Mockear el servicio para que devuelva la entidad Usuario
        when(usuarioService.crearUsuario(
            eq(usuarioCrearDTO.getNombre()),
            eq(usuarioCrearDTO.getCorreoElectronico()),
            eq(usuarioCrearDTO.getContrasena()),
            eq(Perfil.ESTUDIANTE) // El controlador convierte el String a Enum
        )).thenReturn(usuario);

        // Mockear el mapper para que convierta la entidad a DTO.Detalle
        when(usuarioMapper.toDetalle(any(Usuario.class))).thenReturn(usuarioDetalleDTO);

        mockMvc.perform(post("/api/v1/usuarios") // Actualizada la ruta
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioCrearDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(usuarioDetalleDTO.getId()))
                .andExpect(jsonPath("$.nombre").value(usuarioDetalleDTO.getNombre()))
                .andExpect(jsonPath("$.correoElectronico").value(usuarioDetalleDTO.getCorreoElectronico()))
                .andExpect(jsonPath("$.perfil").value(usuarioDetalleDTO.getPerfil()));
    }

    @Test
    public void testObtenerUsuarioPorId() throws Exception {
        String usuarioId = "1";
        when(usuarioService.obtenerUsuarioPorId(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDetalle(any(Usuario.class))).thenReturn(usuarioDetalleDTO);

        mockMvc.perform(get("/api/v1/usuarios/{id}", usuarioId)) // Actualizada la ruta
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioDetalleDTO.getId()))
                .andExpect(jsonPath("$.nombre").value(usuarioDetalleDTO.getNombre()))
                .andExpect(jsonPath("$.correoElectronico").value(usuarioDetalleDTO.getCorreoElectronico()))
                .andExpect(jsonPath("$.perfil").value(usuarioDetalleDTO.getPerfil()));
    }

    @Test
    public void testObtenerUsuarioPorId_NoEncontrado() throws Exception {
        String usuarioId = "99";
        when(usuarioService.obtenerUsuarioPorId(usuarioId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/usuarios/{id}", usuarioId))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testListarUsuarios() throws Exception {
        Page<Usuario> paginaUsuarios = new PageImpl<>(Arrays.asList(usuario));
        when(usuarioService.listarUsuarios(any(Pageable.class))).thenReturn(paginaUsuarios);
        when(usuarioMapper.toListado(any(Usuario.class))).thenReturn(usuarioListadoDTO);

        mockMvc.perform(get("/api/v1/usuarios")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(usuarioListadoDTO.getId()))
                .andExpect(jsonPath("$.content[0].nombre").value(usuarioListadoDTO.getNombre()))
                .andExpect(jsonPath("$.content[0].perfil").value(usuarioListadoDTO.getPerfil()))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
} 