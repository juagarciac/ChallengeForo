package com.Challenge.Foro.service;

import com.Challenge.Foro.model.Usuario;
import com.Challenge.Foro.model.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario crearUsuario(String nombre, String correoElectronico, String contrasena, Perfil perfil);
    Page<Usuario> listarUsuarios(Pageable pageable);
    Optional<Usuario> obtenerUsuarioPorId(String id);
    Usuario actualizarInformacionUsuario(String id, String nombre, Perfil perfil);
    Usuario actualizarCorreoElectronico(String id, String nuevoCorreo);
    Usuario actualizarContrasena(String id, String nuevaContrasena);
    void eliminarUsuario(String id);
} 