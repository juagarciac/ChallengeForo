package com.Challenge.Foro.service.impl;

import com.Challenge.Foro.model.Usuario;
import com.Challenge.Foro.model.Perfil;
import com.Challenge.Foro.repository.UsuarioRepository;
import com.Challenge.Foro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario crearUsuario(String nombre, String correoElectronico, String contrasena, Perfil perfil) {
        // Validar que no exista un usuario con el mismo correo
        if (usuarioRepository.findByCorreoElectronico(correoElectronico).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con el correo: " + correoElectronico);
        }

        // Validar que no exista un usuario con el mismo nombre
        if (usuarioRepository.findByNombre(nombre).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con el nombre: " + nombre);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreoElectronico(correoElectronico);
        usuario.setContrasena(contrasena);
        usuario.setPerfil(perfil);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Page<Usuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario actualizarInformacionUsuario(String id, String nombre, Perfil perfil) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar que el nuevo nombre no esté en uso por otro usuario
        if (!usuario.getNombre().equals(nombre) && 
            usuarioRepository.findByNombre(nombre).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con el nombre: " + nombre);
        }

        usuario.setNombre(nombre);
        usuario.setPerfil(perfil);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizarCorreoElectronico(String id, String nuevoCorreo) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar que el nuevo correo no esté en uso
        if (!usuario.getCorreoElectronico().equals(nuevoCorreo) && 
            usuarioRepository.findByCorreoElectronico(nuevoCorreo).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con el correo: " + nuevoCorreo);
        }

        usuario.setCorreoElectronico(nuevoCorreo);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizarContrasena(String id, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setContrasena(nuevaContrasena);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar que el usuario no tenga tópicos o respuestas asociadas
        if (!usuario.getTopicos().isEmpty() || !usuario.getRespuestas().isEmpty()) {
            throw new RuntimeException("No se puede eliminar el usuario porque tiene tópicos o respuestas asociadas");
        }

        usuarioRepository.deleteById(id);
    }
} 