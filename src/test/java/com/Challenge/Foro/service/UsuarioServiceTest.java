package com.Challenge.Foro.service;

import com.Challenge.Foro.model.Usuario;
import com.Challenge.Foro.model.Perfil;
import com.Challenge.Foro.repository.UsuarioRepository;
import com.Challenge.Foro.service.impl.UsuarioServiceImpl;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("Juan");
        usuario.setCorreoElectronico("juan@email.com");
        usuario.setContrasena("password123");
        usuario.setPerfil(Perfil.ESTUDIANTE);
    }

    @Test
    void testCrearUsuario() {
        when(usuarioRepository.findByCorreoElectronico(any())).thenReturn(Optional.empty());
        when(usuarioRepository.findByNombre(any())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenReturn(usuario);

        Usuario usuarioCreado = usuarioService.crearUsuario(
            "Juan", 
            "juan@email.com", 
            "password123", 
            Perfil.ESTUDIANTE
        );

        assertNotNull(usuarioCreado);
        assertEquals("Juan", usuarioCreado.getNombre());
        assertEquals("juan@email.com", usuarioCreado.getCorreoElectronico());
        assertEquals(Perfil.ESTUDIANTE, usuarioCreado.getPerfil());
    }

    @Test
    void testCrearUsuarioConCorreoExistente() {
        when(usuarioRepository.findByCorreoElectronico("juan@email.com"))
            .thenReturn(Optional.of(usuario));

        assertThrows(RuntimeException.class, () -> 
            usuarioService.crearUsuario(
                "Juan", 
                "juan@email.com", 
                "password123", 
                Perfil.ESTUDIANTE
            )
        );
    }

    @Test
    void testListarUsuarios() {
        List<Usuario> usuariosList = Arrays.asList(usuario);
        Page<Usuario> usuariosPage = new PageImpl<>(usuariosList, PageRequest.of(0, 10), usuariosList.size());
        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(usuariosPage);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Usuario> resultado = usuarioService.listarUsuarios(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Juan", resultado.getContent().get(0).getNombre());
    }

    @Test
    void testObtenerUsuarioPorId() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));

        Optional<Usuario> usuarioEncontrado = usuarioService.obtenerUsuarioPorId("1");

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("Juan", usuarioEncontrado.get().getNombre());
    }

    @Test
    void testActualizarInformacionUsuario() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByNombre("JuanNuevo")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenReturn(usuario);

        Usuario usuarioActualizado = usuarioService.actualizarInformacionUsuario(
            "1", 
            "JuanNuevo", 
            Perfil.PROFESOR
        );

        assertEquals("JuanNuevo", usuarioActualizado.getNombre());
        assertEquals(Perfil.PROFESOR, usuarioActualizado.getPerfil());
    }

    @Test
    void testActualizarCorreoElectronico() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByCorreoElectronico("nuevo@email.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenReturn(usuario);

        Usuario usuarioActualizado = usuarioService.actualizarCorreoElectronico(
            "1", 
            "nuevo@email.com"
        );

        assertEquals("nuevo@email.com", usuarioActualizado.getCorreoElectronico());
    }

    @Test
    void testActualizarContrasena() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        Usuario usuarioActualizado = usuarioService.actualizarContrasena(
            "1", 
            "nuevaPassword123"
        );

        assertEquals("nuevaPassword123", usuarioActualizado.getContrasena());
    }

    @Test
    void testEliminarUsuario() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).deleteById("1");

        usuarioService.eliminarUsuario("1");

        verify(usuarioRepository, times(1)).deleteById("1");
    }

    @Test
    void testEliminarUsuarioNoEncontrado() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
            usuarioService.eliminarUsuario("1")
        );
    }
} 