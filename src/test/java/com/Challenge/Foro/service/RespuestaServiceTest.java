package com.Challenge.Foro.service;

import com.Challenge.Foro.model.Respuesta;
import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.model.Usuario;
import com.Challenge.Foro.repository.RespuestaRepository;
import com.Challenge.Foro.repository.TopicoRepository;
import com.Challenge.Foro.repository.UsuarioRepository;
import com.Challenge.Foro.service.impl.RespuestaServiceImpl;
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
class RespuestaServiceTest {

    @Mock
    private RespuestaRepository respuestaRepository;

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private RespuestaServiceImpl respuestaService;

    private Respuesta respuesta;
    private Topico topico;
    private Usuario usuario;
    private Topico nuevoTopico;
    private Usuario nuevoUsuario;

    @BeforeEach
    void setUp() {
        topico = new Topico();
        topico.setId("1");
        topico.setTitulo("Duda sobre Spring Boot");

        usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("Juan");

        respuesta = new Respuesta();
        respuesta.setId("1");
        respuesta.setMensaje("Para implementar seguridad en Spring Boot puedes usar Spring Security");
        respuesta.setTopico(topico);
        respuesta.setAutor(usuario);
        respuesta.setFechaCreacion("2024-03-15 10:30:00");

        nuevoTopico = new Topico();
        nuevoTopico.setId("2");
        nuevoTopico.setTitulo("Nuevo Tópico");

        nuevoUsuario = new Usuario();
        nuevoUsuario.setId("2");
        nuevoUsuario.setNombre("Maria");
    }

    @Test
    void testCrearRespuesta() {
        when(topicoRepository.findById("1")).thenReturn(Optional.of(topico));
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        when(respuestaRepository.save(any(Respuesta.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(topicoRepository.save(any(Topico.class))).thenReturn(topico);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Respuesta respuestaCreada = respuestaService.crearRespuesta(
            "Para implementar seguridad en Spring Boot puedes usar Spring Security",
            "1", // usuarioId
            "1"  // topicoId
        );

        assertNotNull(respuestaCreada);
        assertEquals("Para implementar seguridad en Spring Boot puedes usar Spring Security", 
            respuestaCreada.getMensaje());
        assertEquals(topico, respuestaCreada.getTopico());
        assertEquals(usuario, respuestaCreada.getAutor());
    }

    @Test
    void testCrearRespuestaConTopicoNoEncontrado() {
        when(topicoRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            respuestaService.crearRespuesta(
                "Para implementar seguridad en Spring Boot puedes usar Spring Security",
                "1",
                "1"
            )
        );
    }

    @Test
    void testListarRespuestas() {
        List<Respuesta> respuestasList = Arrays.asList(respuesta);
        Page<Respuesta> respuestasPage = new PageImpl<>(respuestasList, PageRequest.of(0, 10), respuestasList.size());
        when(respuestaRepository.findAll(any(Pageable.class))).thenReturn(respuestasPage);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Respuesta> resultado = respuestaService.listarRespuestas(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Para implementar seguridad en Spring Boot puedes usar Spring Security", 
            resultado.getContent().get(0).getMensaje());
    }

    @Test
    void testListarRespuestasPorTopico() {
        List<Respuesta> respuestas = Arrays.asList(respuesta);
        topico.setRespuestas(respuestas);
        when(topicoRepository.findById("1")).thenReturn(Optional.of(topico));

        List<Respuesta> respuestasListadas = respuestaService.listarRespuestasPorTopico("1");

        assertEquals(1, respuestasListadas.size());
        assertEquals("Para implementar seguridad en Spring Boot puedes usar Spring Security", 
            respuestasListadas.get(0).getMensaje());
    }

    @Test
    void testObtenerRespuestaPorId() {
        when(respuestaRepository.findById("1")).thenReturn(Optional.of(respuesta));

        Optional<Respuesta> respuestaEncontrada = respuestaService.obtenerRespuestaPorId("1");

        assertTrue(respuestaEncontrada.isPresent());
        assertEquals("Para implementar seguridad en Spring Boot puedes usar Spring Security", 
            respuestaEncontrada.get().getMensaje());
    }

    @Test
    void testActualizarRespuesta() {
        when(respuestaRepository.findById("1")).thenReturn(Optional.of(respuesta));
        when(usuarioRepository.findById("2")).thenReturn(Optional.of(nuevoUsuario));
        when(topicoRepository.findById("2")).thenReturn(Optional.of(nuevoTopico));
        
        when(usuarioRepository.save(eq(usuario))).thenReturn(usuario);
        when(topicoRepository.save(eq(topico))).thenReturn(topico);
        when(usuarioRepository.save(eq(nuevoUsuario))).thenReturn(nuevoUsuario);
        when(topicoRepository.save(eq(nuevoTopico))).thenReturn(nuevoTopico);

        when(respuestaRepository.save(any(Respuesta.class))).thenAnswer(invocation -> {
            Respuesta r = invocation.getArgument(0);
            r.setId("1");
            return r;
        });

        Respuesta respuestaActualizada = respuestaService.actualizarRespuesta(
            "1",
            "Nuevo mensaje",
            "2",
            "2"
        );
        assertNotNull(respuestaActualizada);
        assertEquals("Nuevo mensaje", respuestaActualizada.getMensaje());
        assertEquals(nuevoUsuario, respuestaActualizada.getAutor());
        assertEquals(nuevoTopico, respuestaActualizada.getTopico());
    }

    @Test
    void testEliminarRespuesta() {
        when(respuestaRepository.findById("1")).thenReturn(Optional.of(respuesta));
        when(topicoRepository.save(any())).thenReturn(topico);
        when(usuarioRepository.save(any())).thenReturn(usuario);
        doNothing().when(respuestaRepository).deleteById("1");

        respuestaService.eliminarRespuesta("1");

        verify(respuestaRepository, times(1)).deleteById("1");
    }

    @Test
    void testEliminarRespuestaNoEncontrada() {
        when(respuestaRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            respuestaService.eliminarRespuesta("1")
        );
    }
} 