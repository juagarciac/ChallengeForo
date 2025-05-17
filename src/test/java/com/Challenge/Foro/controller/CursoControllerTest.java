package com.Challenge.Foro.controller;

import com.Challenge.Foro.dto.curso.CursoDTO;
import com.Challenge.Foro.dto.curso.CursoMapper;
import com.Challenge.Foro.model.Curso;
import com.Challenge.Foro.service.CursoService;
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

@WebMvcTest(CursoController.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @MockBean
    private CursoMapper cursoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Curso curso;
    private CursoDTO.Crear cursoCrearDTO;
    private CursoDTO.Actualizar cursoActualizarDTO;
    private CursoDTO.Detalle cursoDetalleDTO;
    private CursoDTO.Listado cursoListadoDTO;

    @BeforeEach
    void setUp() {
        curso = new Curso("Spring Boot", "Programación");
        curso.setId("1");

        cursoCrearDTO = new CursoDTO.Crear();
        cursoCrearDTO.setNombre("Spring Boot");
        cursoCrearDTO.setCategoria("Programación");

        cursoActualizarDTO = new CursoDTO.Actualizar();
        cursoActualizarDTO.setNombre("Spring Boot Actualizado");
        cursoActualizarDTO.setCategoria("Backend Avanzado");

        cursoDetalleDTO = new CursoDTO.Detalle();
        cursoDetalleDTO.setId("1");
        cursoDetalleDTO.setNombre("Spring Boot");
        cursoDetalleDTO.setCategoria("Programación");

        cursoListadoDTO = new CursoDTO.Listado();
        cursoListadoDTO.setId("1");
        cursoListadoDTO.setNombre("Spring Boot");
        cursoListadoDTO.setCategoria("Programación");
    }

    @Test
    public void testCrearCurso() throws Exception {
        when(cursoService.crearCurso(anyString(), anyString())).thenReturn(curso);
        when(cursoMapper.toDetalle(any(Curso.class))).thenReturn(cursoDetalleDTO);

        mockMvc.perform(post("/api/v1/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoCrearDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(cursoDetalleDTO.getId()))
                .andExpect(jsonPath("$.nombre").value(cursoDetalleDTO.getNombre()))
                .andExpect(jsonPath("$.categoria").value(cursoDetalleDTO.getCategoria()));
    }

    @Test
    public void testListarCursos() throws Exception {
        Page<Curso> paginaCursos = new PageImpl<>(Arrays.asList(curso));
        when(cursoService.listarCursos(any(Pageable.class))).thenReturn(paginaCursos);
        when(cursoMapper.toListado(any(Curso.class))).thenReturn(cursoListadoDTO);

        mockMvc.perform(get("/api/v1/cursos")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(cursoListadoDTO.getId()))
                .andExpect(jsonPath("$.content[0].nombre").value(cursoListadoDTO.getNombre()))
                .andExpect(jsonPath("$.content[0].categoria").value(cursoListadoDTO.getCategoria()))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    public void testObtenerCursoPorId() throws Exception {
        when(cursoService.obtenerCursoPorId("1")).thenReturn(Optional.of(curso));
        when(cursoMapper.toDetalle(any(Curso.class))).thenReturn(cursoDetalleDTO);

        mockMvc.perform(get("/api/v1/cursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cursoDetalleDTO.getId()))
                .andExpect(jsonPath("$.nombre").value(cursoDetalleDTO.getNombre()))
                .andExpect(jsonPath("$.categoria").value(cursoDetalleDTO.getCategoria()));
    }
    
    @Test
    public void testObtenerCursoPorId_NoEncontrado() throws Exception {
        when(cursoService.obtenerCursoPorId("99")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/cursos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testActualizarCurso() throws Exception {
        when(cursoService.obtenerCursoPorId("1")).thenReturn(Optional.of(curso));
        Curso cursoModificado = new Curso(cursoActualizarDTO.getNombre(), cursoActualizarDTO.getCategoria());
        cursoModificado.setId("1");
        
        CursoDTO.Detalle cursoDetalleActualizado = new CursoDTO.Detalle();
        cursoDetalleActualizado.setId("1");
        cursoDetalleActualizado.setNombre(cursoActualizarDTO.getNombre());
        cursoDetalleActualizado.setCategoria(cursoActualizarDTO.getCategoria());

        when(cursoService.actualizarCurso(eq("1"), anyString(), anyString())).thenReturn(cursoModificado);
        when(cursoMapper.toDetalle(any(Curso.class))).thenReturn(cursoDetalleActualizado);

        mockMvc.perform(put("/api/v1/cursos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoActualizarDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value(cursoActualizarDTO.getNombre()))
                .andExpect(jsonPath("$.categoria").value(cursoActualizarDTO.getCategoria()));
    }

    @Test
    public void testEliminarCurso() throws Exception {
        mockMvc.perform(delete("/api/v1/cursos/1"))
                .andExpect(status().isNoContent());
    }
}

