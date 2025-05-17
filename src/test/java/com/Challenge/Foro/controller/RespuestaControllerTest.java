package com.Challenge.Foro.controller;

import com.Challenge.Foro.dto.respuesta.RespuestaDTO;
import com.Challenge.Foro.dto.respuesta.RespuestaMapper;
import com.Challenge.Foro.model.Respuesta;
import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.model.Usuario;
import com.Challenge.Foro.service.RespuestaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RespuestaController.class)
public class RespuestaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RespuestaService respuestaService;

    @MockBean
    private RespuestaMapper respuestaMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Respuesta respuesta;
    private Topico topico;
    private Usuario usuario;
    private RespuestaDTO.Crear respuestaCrearDTO;
    private RespuestaDTO.Actualizar respuestaActualizarDTO;
    private RespuestaDTO.Detalle respuestaDetalleDTO;
    private RespuestaDTO.Listado respuestaListadoDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId("user1");
        usuario.setNombre("Test User");

        topico = new Topico();
        topico.setId("topic1");

        respuesta = new Respuesta();
        respuesta.setId("resp1");
        respuesta.setMensaje("Test Respuesta");
        respuesta.setAutor(usuario);
        respuesta.setTopico(topico);

        respuestaCrearDTO = new RespuestaDTO.Crear();
        respuestaCrearDTO.setMensaje("Test Respuesta");
        respuestaCrearDTO.setUsuarioId("user1"); // Asumimos que el DTO lleva el usuarioId
        // topicoId se toma de la ruta en el controlador, no es parte de este DTO Crear específico.

        respuestaActualizarDTO = new RespuestaDTO.Actualizar();
        respuestaActualizarDTO.setMensaje("Respuesta Actualizada");

        respuestaDetalleDTO = new RespuestaDTO.Detalle();
        respuestaDetalleDTO.setId("resp1");
        respuestaDetalleDTO.setMensaje("Test Respuesta");
        respuestaDetalleDTO.setAutor(usuario.getNombre());
        respuestaDetalleDTO.setTopicoId(topico.getId());

        respuestaListadoDTO = new RespuestaDTO.Listado();
        respuestaListadoDTO.setId("resp1");
        respuestaListadoDTO.setMensaje("Test Respuesta");
        respuestaListadoDTO.setAutor(usuario.getNombre());
    }

    @Test
    public void testCrearRespuesta() throws Exception {
        // El controlador extrae topicoId de la ruta y usuarioId del DTO CrearRespuesta
        when(respuestaService.crearRespuesta(
            eq(respuestaCrearDTO.getMensaje()),
            eq(respuestaCrearDTO.getUsuarioId()), 
            eq("topic1") // topicoId de la ruta
        )).thenReturn(respuesta);
        when(respuestaMapper.toDetalle(any(Respuesta.class))).thenReturn(respuestaDetalleDTO);

        mockMvc.perform(post("/api/v1/topicos/{topicoId}/respuestas", "topic1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(respuestaCrearDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(respuestaDetalleDTO.getId()))
                .andExpect(jsonPath("$.mensaje").value(respuestaDetalleDTO.getMensaje()))
                .andExpect(jsonPath("$.autor").value(respuestaDetalleDTO.getAutor()));
    }

    @Test
    public void testListarRespuestasPorTopico() throws Exception {
        when(respuestaService.listarRespuestasPorTopico("topic1")).thenReturn(Collections.singletonList(respuesta));
        when(respuestaMapper.toListado(any(Respuesta.class))).thenReturn(respuestaListadoDTO);

        mockMvc.perform(get("/api/v1/topicos/{topicoId}/respuestas", "topic1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(respuestaListadoDTO.getId()))
                .andExpect(jsonPath("$[0].mensaje").value(respuestaListadoDTO.getMensaje()))
                .andExpect(jsonPath("$[0].autor").value(respuestaListadoDTO.getAutor()));
    }

    @Test
    public void testObtenerRespuestaPorId() throws Exception {
        when(respuestaService.obtenerRespuestaPorId(eq("resp1"))).thenReturn(Optional.of(respuesta));
        when(respuestaMapper.toDetalle(any(Respuesta.class))).thenReturn(respuestaDetalleDTO);

        mockMvc.perform(get("/api/v1/respuestas/{id}", "resp1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(respuestaDetalleDTO.getId()))
                .andExpect(jsonPath("$.mensaje").value(respuestaDetalleDTO.getMensaje()))
                .andExpect(jsonPath("$.autor").value(respuestaDetalleDTO.getAutor()));
    }

    @Test
    public void testObtenerRespuestaPorId_NoEncontrado() throws Exception {
        when(respuestaService.obtenerRespuestaPorId(eq("resp99"))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/respuestas/{id}", "resp99"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testActualizarRespuesta() throws Exception {
        when(respuestaService.obtenerRespuestaPorId(eq("resp1"))).thenReturn(Optional.of(respuesta));
        Respuesta respuestaModificada = new Respuesta();
        respuestaModificada.setId("resp1");
        respuestaModificada.setMensaje(respuestaActualizarDTO.getMensaje());
        respuestaModificada.setAutor(usuario); // Asumimos que autor y tópico no cambian con este endpoint
        respuestaModificada.setTopico(topico);

        RespuestaDTO.Detalle detalleActualizado = new RespuestaDTO.Detalle();
        detalleActualizado.setId("resp1");
        detalleActualizado.setMensaje(respuestaActualizarDTO.getMensaje());
        detalleActualizado.setAutor(usuario.getNombre());
        detalleActualizado.setTopicoId(topico.getId());

        when(respuestaService.actualizarRespuesta(eq("resp1"), eq(respuestaActualizarDTO.getMensaje()), any(), any()))
            .thenReturn(respuestaModificada);
        when(respuestaMapper.toDetalle(any(Respuesta.class))).thenReturn(detalleActualizado);

        mockMvc.perform(put("/api/v1/respuestas/{id}", "resp1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(respuestaActualizarDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(detalleActualizado.getId()))
                .andExpect(jsonPath("$.mensaje").value(detalleActualizado.getMensaje()));
    }

    @Test
    public void testEliminarRespuesta() throws Exception {
        mockMvc.perform(delete("/api/v1/respuestas/1"))
                .andExpect(status().isNoContent());
    }
}

