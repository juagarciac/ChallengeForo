package com.Challenge.Foro.service;

import com.Challenge.Foro.model.Curso;
import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.repository.CursoRepository;
import com.Challenge.Foro.repository.TopicoRepository;
import com.Challenge.Foro.service.impl.TopicoServiceImpl;
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
class TopicoServiceTest {

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private TopicoServiceImpl topicoService;

    private Topico topico;
    private Curso curso;

    @BeforeEach
    void setUp() {
        curso = new Curso();
        curso.setId("1");
        curso.setNombre("Spring Boot");
        curso.setCategoria("Backend");

        topico = new Topico();
        topico.setId("1");
        topico.setTitulo("Duda sobre Spring Boot");
        topico.setMensaje("¿Cómo implementar seguridad en Spring Boot?");
        topico.setAutor("Juan");
        topico.setCurso(curso);
    }

    @Test
    void testCrearTopico() {
        when(cursoRepository.findById("1")).thenReturn(Optional.of(curso));
        when(topicoRepository.save(any())).thenReturn(topico);
        when(cursoRepository.save(any())).thenReturn(curso);

        Topico topicoCreado = topicoService.crearTopico(
            "Duda sobre Spring Boot",
            "¿Cómo implementar seguridad en Spring Boot?",
            "Juan",
            "1"
        );

        assertNotNull(topicoCreado);
        assertEquals("Duda sobre Spring Boot", topicoCreado.getTitulo());
        assertEquals("¿Cómo implementar seguridad en Spring Boot?", topicoCreado.getMensaje());
        assertEquals("Juan", topicoCreado.getAutor());
        assertEquals(curso, topicoCreado.getCurso());
    }

    @Test
    void testCrearTopicoConCursoNoEncontrado() {
        when(cursoRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            topicoService.crearTopico(
                "Duda sobre Spring Boot",
                "¿Cómo implementar seguridad en Spring Boot?",
                "Juan",
                "1"
            )
        );
    }

    @Test
    void testListarTopicos() {
        List<Topico> topicosList = Arrays.asList(topico);
        Page<Topico> topicosPage = new PageImpl<>(topicosList, PageRequest.of(0, 10), topicosList.size());
        when(topicoRepository.findAll(any(Pageable.class))).thenReturn(topicosPage);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Topico> resultado = topicoService.listarTopicos(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Duda sobre Spring Boot", resultado.getContent().get(0).getTitulo());
    }

    @Test
    void testObtenerTopicoPorId() {
        when(topicoRepository.findById("1")).thenReturn(Optional.of(topico));

        Optional<Topico> topicoEncontrado = topicoService.obtenerTopicoPorId("1");

        assertTrue(topicoEncontrado.isPresent());
        assertEquals("Duda sobre Spring Boot", topicoEncontrado.get().getTitulo());
    }

    @Test
    void testActualizarTopico() {
        Curso nuevoCurso = new Curso();
        nuevoCurso.setId("2");
        nuevoCurso.setNombre("Spring Security");

        when(topicoRepository.findById("1")).thenReturn(Optional.of(topico));
        when(cursoRepository.findById("2")).thenReturn(Optional.of(nuevoCurso));
        when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(topicoRepository.save(any(Topico.class))).thenAnswer(invocation -> {
            Topico t = invocation.getArgument(0);
            t.setId("1");
            return t;
        });

        Topico topicoActualizado = topicoService.actualizarTopico(
            "1",
            "Nueva duda",
            "Nuevo mensaje",
            "Maria",
            "2"
        );

        assertNotNull(topicoActualizado);
        assertEquals("Nueva duda", topicoActualizado.getTitulo());
        assertEquals("Nuevo mensaje", topicoActualizado.getMensaje());
        assertEquals("Maria", topicoActualizado.getAutor());
        assertEquals(nuevoCurso, topicoActualizado.getCurso());
    }

    @Test
    void testEliminarTopico() {
        when(topicoRepository.findById("1")).thenReturn(Optional.of(topico));
        when(cursoRepository.save(any())).thenReturn(curso);
        doNothing().when(topicoRepository).deleteById("1");

        topicoService.eliminarTopico("1");

        verify(topicoRepository, times(1)).deleteById("1");
    }

    @Test
    void testEliminarTopicoNoEncontrado() {
        when(topicoRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            topicoService.eliminarTopico("1")
        );
    }
} 