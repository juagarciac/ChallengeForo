package com.Challenge.Foro.service;

import com.Challenge.Foro.model.Curso;
import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.repository.CursoRepository;
import com.Challenge.Foro.service.impl.CursoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoServiceImpl cursoService;

    private Curso curso;

    @BeforeEach
    void setUp() {
        curso = new Curso();
        curso.setId("1");
        curso.setNombre("Spring Boot");
        curso.setCategoria("Backend");
    }

    @Test
    void testCrearCurso() {
        when(cursoRepository.findByNombre(any())).thenReturn(Optional.empty());
        when(cursoRepository.save(any())).thenReturn(curso);

        Curso cursoCreado = cursoService.crearCurso("Spring Boot", "Backend");

        assertNotNull(cursoCreado);
        assertEquals("Spring Boot", cursoCreado.getNombre());
        assertEquals("Backend", cursoCreado.getCategoria());
    }

    @Test
    void testCrearCursoConNombreExistente() {
        when(cursoRepository.findByNombre("Spring Boot")).thenReturn(Optional.of(curso));

        assertThrows(RuntimeException.class, () ->
            cursoService.crearCurso("Spring Boot", "Backend")
        );
    }

    @Test
    void testListarCursos() {
        List<Curso> cursosList = Arrays.asList(curso);
        Page<Curso> cursosPage = new PageImpl<>(cursosList, PageRequest.of(0, 10), cursosList.size());
        when(cursoRepository.findAll(any(Pageable.class))).thenReturn(cursosPage);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Curso> resultado = cursoService.listarCursos(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Spring Boot", resultado.getContent().get(0).getNombre());
    }

    @Test
    void testObtenerCursoPorId() {
        when(cursoRepository.findById("1")).thenReturn(Optional.of(curso));

        Optional<Curso> cursoEncontrado = cursoService.obtenerCursoPorId("1");

        assertTrue(cursoEncontrado.isPresent());
        assertEquals("Spring Boot", cursoEncontrado.get().getNombre());
    }

    @Test
    void testActualizarCurso() {
        when(cursoRepository.findById("1")).thenReturn(Optional.of(curso));
        when(cursoRepository.findByNombre("Spring Security")).thenReturn(Optional.empty());
        when(cursoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Curso cursoActualizado = cursoService.actualizarCurso("1", "Spring Security", "Seguridad");

        assertNotNull(cursoActualizado);
        assertEquals("Spring Security", cursoActualizado.getNombre());
        assertEquals("Seguridad", cursoActualizado.getCategoria());
    }

    @Test
    void testActualizarCursoConNombreExistente() {
        Curso otroCurso = new Curso();
        otroCurso.setId("2");
        otroCurso.setNombre("Spring Security");

        when(cursoRepository.findById("1")).thenReturn(Optional.of(curso));
        when(cursoRepository.findByNombre("Spring Security")).thenReturn(Optional.of(otroCurso));

        assertThrows(RuntimeException.class, () ->
            cursoService.actualizarCurso("1", "Spring Security", "Backend")
        );
    }

    @Test
    void testEliminarCurso() {
        when(cursoRepository.findById("1")).thenReturn(Optional.of(curso));
        doNothing().when(cursoRepository).deleteById("1");

        cursoService.eliminarCurso("1");

        verify(cursoRepository, times(1)).deleteById("1");
    }

    @Test
    void testEliminarCursoConTopicos() {
        Curso cursoConTopicos = new Curso();
        cursoConTopicos.setId("1");
        cursoConTopicos.setNombre("Spring Boot");
        cursoConTopicos.getTopicos().add(new Topico());

        when(cursoRepository.findById("1")).thenReturn(Optional.of(cursoConTopicos));

        assertThrows(RuntimeException.class, () ->
            cursoService.eliminarCurso("1")
        );
    }

    @Test
    void testEliminarCursoNoEncontrado() {
        when(cursoRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            cursoService.eliminarCurso("1")
        );
    }
} 