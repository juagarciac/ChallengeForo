package com.Challenge.Foro.repository;

import com.Challenge.Foro.model.Usuario;
import com.Challenge.Foro.model.Perfil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
class UsuarioRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testGuardarYBuscarUsuario() {
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setCorreoElectronico("juan@email.com");
        usuario.setContrasena("password123");
        usuario.setPerfil(Perfil.ESTUDIANTE);

        // Guardar usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Buscar usuario por nombre
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByNombre("Juan");
        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("Juan", usuarioEncontrado.get().getNombre());
        assertEquals("juan@email.com", usuarioEncontrado.get().getCorreoElectronico());
    }

    @Test
    void testBuscarPorCorreoElectronico() {
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Maria");
        usuario.setCorreoElectronico("maria@email.com");
        usuario.setContrasena("password123");
        usuario.setPerfil(Perfil.ESTUDIANTE);

        // Guardar usuario
        usuarioRepository.save(usuario);

        // Buscar usuario por correo electrónico
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByCorreoElectronico("maria@email.com");
        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("Maria", usuarioEncontrado.get().getNombre());
        assertEquals("maria@email.com", usuarioEncontrado.get().getCorreoElectronico());
    }

    @Test
    void testNoEncontrarUsuario() {
        Optional<Usuario> usuarioNoEncontrado = usuarioRepository.findByNombre("UsuarioInexistente");
        assertFalse(usuarioNoEncontrado.isPresent());
    }
} 