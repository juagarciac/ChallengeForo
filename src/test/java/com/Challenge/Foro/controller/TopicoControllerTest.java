package com.Challenge.Foro.controller;

import com.Challenge.Foro.dto.topico.TopicoDTO;
import com.Challenge.Foro.dto.topico.TopicoMapper;
// Si se necesita RespuestaMapper explícitamente en el test, importarlo. Sino, TopicoMapper ya lo usa.
// import com.Challenge.Foro.dto.respuesta.RespuestaMapper;
import com.Challenge.Foro.model.Curso;
import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.service.TopicoService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TopicoController.class)
public class TopicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicoService topicoService;

    @MockBean
    private TopicoMapper topicoMapper;

    // @MockBean si RespuestaMapper se usa directamente en el controlador, sino no es necesario aquí.
    // private RespuestaMapper respuestaMapper; 

    @Autowired
    private ObjectMapper objectMapper;

    private Topico topico;
    private Curso curso;
    private TopicoDTO.Crear topicoCrearDTO;
    private TopicoDTO.Detalle topicoDetalleDTO;
    private TopicoDTO.Listado topicoListadoDTO;

    @BeforeEach
    void setUp() {
        curso = new Curso("Spring Boot", "Programación");
        curso.setId("1");

        topico = new Topico("Test Tópico", "Este es un mensaje de prueba", "Test User", curso);
        topico.setId("1");

        topicoCrearDTO = new TopicoDTO.Crear();
        topicoCrearDTO.setTitulo("Test Tópico");
        topicoCrearDTO.setMensaje("Este es un mensaje de prueba");
        topicoCrearDTO.setAutor("Test User");
        topicoCrearDTO.setCursoId("1");

        topicoDetalleDTO = new TopicoDTO.Detalle();
        topicoDetalleDTO.setId("1");
        topicoDetalleDTO.setTitulo("Test Tópico");
        topicoDetalleDTO.setMensaje("Este es un mensaje de prueba");
        topicoDetalleDTO.setAutor("Test User");
        topicoDetalleDTO.setCursoId("1");
        // topicoDetalleDTO.setRespuestas(...); // Si se necesitan respuestas en el DTO de detalle

        topicoListadoDTO = new TopicoDTO.Listado();
        topicoListadoDTO.setId("1");
        topicoListadoDTO.setTitulo("Test Tópico");
        topicoListadoDTO.setAutor("Test User");
        topicoListadoDTO.setCursoNombre("Spring Boot");
    }

    @Test
    public void testCrearTopico() throws Exception {
        when(topicoService.crearTopico(
            eq(topicoCrearDTO.getTitulo()),
            eq(topicoCrearDTO.getMensaje()),
            eq(topicoCrearDTO.getAutor()),
            eq(topicoCrearDTO.getCursoId())
        )).thenReturn(topico);
        when(topicoMapper.toDetalle(any(Topico.class))).thenReturn(topicoDetalleDTO);

        mockMvc.perform(post("/api/v1/topicos") // Ruta actualizada
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(topicoCrearDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(topicoDetalleDTO.getId()))
                .andExpect(jsonPath("$.titulo").value(topicoDetalleDTO.getTitulo()))
                .andExpect(jsonPath("$.mensaje").value(topicoDetalleDTO.getMensaje()));
    }

    @Test
    public void testObtenerTopicoPorId() throws Exception {
        String topicoId = "1";
        when(topicoService.obtenerTopicoPorId(topicoId)).thenReturn(Optional.of(topico));
        when(topicoMapper.toDetalle(any(Topico.class))).thenReturn(topicoDetalleDTO);

        mockMvc.perform(get("/api/v1/topicos/{id}", topicoId)) // Ruta actualizada
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(topicoDetalleDTO.getId()))
                .andExpect(jsonPath("$.titulo").value(topicoDetalleDTO.getTitulo()))
                .andExpect(jsonPath("$.mensaje").value(topicoDetalleDTO.getMensaje()));
    }
    
    @Test
    public void testObtenerTopicoPorId_NoEncontrado() throws Exception {
        String topicoId = "99";
        when(topicoService.obtenerTopicoPorId(topicoId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/topicos/{id}", topicoId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testListarTopicos() throws Exception {
        Page<Topico> paginaTopicos = new PageImpl<>(Arrays.asList(topico));
        when(topicoService.listarTopicos(any(Pageable.class))).thenReturn(paginaTopicos);
        when(topicoMapper.toListado(any(Topico.class))).thenReturn(topicoListadoDTO);

        mockMvc.perform(get("/api/v1/topicos") // Ruta actualizada
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(topicoListadoDTO.getId()))
                .andExpect(jsonPath("$.content[0].titulo").value(topicoListadoDTO.getTitulo()))
                .andExpect(jsonPath("$.content[0].autor").value(topicoListadoDTO.getAutor()))
                .andExpect(jsonPath("$.content[0].cursoNombre").value(topicoListadoDTO.getCursoNombre()))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
} 