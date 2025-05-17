package com.Challenge.Foro.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class CursoTest {

    @Test
    void testCrearCurso() {
        Curso curso = new Curso();
        curso.setId("1");
        curso.setNombre("Spring Boot");
        curso.setCategoria("Backend");

        assertEquals("1", curso.getId());
        assertEquals("Spring Boot", curso.getNombre());
        assertEquals("Backend", curso.getCategoria());
    }

    @Test
    void testCursoConConstructorCompleto() {
        Curso curso = new Curso("Spring Boot", "Backend");
        
        assertEquals("Spring Boot", curso.getNombre());
        assertEquals("Backend", curso.getCategoria());
        assertNotNull(curso.getTopicos());
    }

    @Test
    void testCursoConTopicos() {
        Curso curso = new Curso();
        ArrayList<Topico> topicos = new ArrayList<>();
        Topico topico = new Topico();
        topico.setId("1");
        topico.setTitulo("Duda sobre Spring Boot");
        topicos.add(topico);
        
        curso.setTopicos(topicos);
        
        assertEquals(1, curso.getTopicos().size());
        assertEquals("1", curso.getTopicos().get(0).getId());
        assertEquals("Duda sobre Spring Boot", curso.getTopicos().get(0).getTitulo());
    }
} 