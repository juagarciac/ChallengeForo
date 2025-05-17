package com.Challenge.Foro.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class TopicoTest {

    @Test
    void testCrearTopico() {
        Topico topico = new Topico();
        topico.setId("1");
        topico.setTitulo("Duda sobre Spring Boot");
        topico.setMensaje("¿Cómo implementar seguridad en Spring Boot?");
        topico.setAutor("Juan");
        topico.setStatus("NO_RESPONDIDO");

        assertEquals("1", topico.getId());
        assertEquals("Duda sobre Spring Boot", topico.getTitulo());
        assertEquals("¿Cómo implementar seguridad en Spring Boot?", topico.getMensaje());
        assertEquals("Juan", topico.getAutor());
        assertEquals("NO_RESPONDIDO", topico.getStatus());
    }

    @Test
    void testTopicoConConstructorCompleto() {
        Curso curso = new Curso();
        curso.setId("1");
        curso.setNombre("Spring Boot");
        
        Topico topico = new Topico("Duda sobre Spring Boot", 
                                 "¿Cómo implementar seguridad en Spring Boot?", 
                                 "Juan", 
                                 curso);
        
        assertEquals("Duda sobre Spring Boot", topico.getTitulo());
        assertEquals("¿Cómo implementar seguridad en Spring Boot?", topico.getMensaje());
        assertEquals("Juan", topico.getAutor());
        assertNotNull(topico.getFechaCreacion());
        assertNotNull(topico.getRespuestas());
        assertEquals(curso, topico.getCurso());
    }

    @Test
    void testFechaFormateada() {
        Topico topico = new Topico();
        LocalDateTime fecha = LocalDateTime.of(2024, 3, 15, 10, 30, 0);
        topico.setFechaCreacion(fecha);
        
        assertEquals("2024-03-15 10:30:00", topico.getFechaFormateada());
    }
} 