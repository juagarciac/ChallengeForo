package com.Challenge.Foro.repository;

import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.model.Curso;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
class TopicoRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private TopicoRepository topicoRepository;

    @BeforeEach
    void cleanDatabase() {
        topicoRepository.deleteAll();
    }

    @Test
    void testGuardarYBuscarTopico() {
        // Crear curso
        Curso curso = new Curso();
        curso.setNombre("Spring Boot");
        curso.setCategoria("Backend");

        // Crear tópico
        Topico topico = new Topico();
        topico.setTitulo("Duda sobre Spring Boot");
        topico.setMensaje("¿Cómo implementar seguridad en Spring Boot?");
        topico.setAutor("Juan");
        topico.setCurso(curso);

        // Guardar tópico
        Topico topicoGuardado = topicoRepository.save(topico);

        // Buscar tópico por ID
        Optional<Topico> topicoEncontrado = topicoRepository.findById(topicoGuardado.getId());
        assertTrue(topicoEncontrado.isPresent());
        assertEquals("Duda sobre Spring Boot", topicoEncontrado.get().getTitulo());
        assertEquals("¿Cómo implementar seguridad en Spring Boot?", topicoEncontrado.get().getMensaje());
        assertEquals("Juan", topicoEncontrado.get().getAutor());
    }

    @Test
    void testListarTopicos() {
        // Crear curso
        Curso curso = new Curso();
        curso.setNombre("Spring Boot");
        curso.setCategoria("Backend");

        // Crear tópicos
        Topico topico1 = new Topico();
        topico1.setTitulo("Duda 1");
        topico1.setMensaje("Mensaje 1");
        topico1.setAutor("Juan");
        topico1.setCurso(curso);

        Topico topico2 = new Topico();
        topico2.setTitulo("Duda 2");
        topico2.setMensaje("Mensaje 2");
        topico2.setAutor("Maria");
        topico2.setCurso(curso);

        // Guardar tópicos
        topicoRepository.save(topico1);
        topicoRepository.save(topico2);

        // Listar tópicos
        List<Topico> topicos = topicoRepository.findAll();
        assertEquals(2, topicos.size());
    }

    @Test
    void testEliminarTopico() {
        // Crear curso
        Curso curso = new Curso();
        curso.setNombre("Spring Boot");
        curso.setCategoria("Backend");

        // Crear tópico
        Topico topico = new Topico();
        topico.setTitulo("Duda sobre Spring Boot");
        topico.setMensaje("¿Cómo implementar seguridad en Spring Boot?");
        topico.setAutor("Juan");
        topico.setCurso(curso);

        // Guardar tópico
        Topico topicoGuardado = topicoRepository.save(topico);

        // Eliminar tópico
        topicoRepository.deleteById(topicoGuardado.getId());

        // Verificar que se eliminó
        Optional<Topico> topicoEliminado = topicoRepository.findById(topicoGuardado.getId());
        assertFalse(topicoEliminado.isPresent());
    }
}

