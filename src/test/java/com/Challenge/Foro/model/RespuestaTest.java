package com.Challenge.Foro.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RespuestaTest {

    @Test
    void testCrearRespuesta() {
        Respuesta respuesta = new Respuesta();
        respuesta.setId("1");
        respuesta.setMensaje("Para implementar seguridad en Spring Boot puedes usar Spring Security");
        respuesta.setFechaCreacion("2024-03-15 10:30:00");
        respuesta.setSolucion("SI");

        assertEquals("1", respuesta.getId());
        assertEquals("Para implementar seguridad en Spring Boot puedes usar Spring Security", respuesta.getMensaje());
        assertEquals("2024-03-15 10:30:00", respuesta.getFechaCreacion());
        assertEquals("SI", respuesta.getSolucion());
    }

    @Test
    void testRespuestaConConstructorCompleto() {
        Usuario autor = new Usuario();
        autor.setId("1");
        autor.setNombre("Juan");

        Topico topico = new Topico();
        topico.setId("1");
        topico.setTitulo("Duda sobre Spring Boot");

        Respuesta respuesta = new Respuesta(
            "Para implementar seguridad en Spring Boot puedes usar Spring Security",
            topico,
            "2024-03-15 10:30:00",
            autor,
            "SI"
        );
        
        assertEquals("Para implementar seguridad en Spring Boot puedes usar Spring Security", respuesta.getMensaje());
        assertEquals(topico, respuesta.getTopico());
        assertEquals("2024-03-15 10:30:00", respuesta.getFechaCreacion());
        assertEquals(autor, respuesta.getAutor());
        assertEquals("SI", respuesta.getSolucion());
    }

    @Test
    void testRespuestaConRelaciones() {
        Respuesta respuesta = new Respuesta();
        
        Usuario autor = new Usuario();
        autor.setId("1");
        autor.setNombre("Juan");
        
        Topico topico = new Topico();
        topico.setId("1");
        topico.setTitulo("Duda sobre Spring Boot");
        
        respuesta.setAutor(autor);
        respuesta.setTopico(topico);
        
        assertEquals(autor, respuesta.getAutor());
        assertEquals(topico, respuesta.getTopico());
    }
} 