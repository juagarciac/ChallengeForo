package com.Challenge.Foro.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testCrearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("Juan");
        usuario.setCorreoElectronico("juan@email.com");
        usuario.setContrasena("password123");

        assertEquals("1", usuario.getId());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("juan@email.com", usuario.getCorreoElectronico());
        assertEquals("password123", usuario.getContrasena());
    }

    @Test
    void testUsuarioConPerfil() {
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("Juan");
        usuario.setPerfil(Perfil.ESTUDIANTE);

        assertEquals(Perfil.ESTUDIANTE, usuario.getPerfil());
    }

    @Test
    void testUsuarioConConstructorCompleto() {
        Usuario usuario = new Usuario("Juan", "password123", "juan@email.com", null, null);
        
        assertEquals("Juan", usuario.getNombre());
        assertEquals("password123", usuario.getContrasena());
        assertEquals("juan@email.com", usuario.getCorreoElectronico());
        assertNotNull(usuario.getTopicos());
        assertNotNull(usuario.getRespuestas());
    }
}