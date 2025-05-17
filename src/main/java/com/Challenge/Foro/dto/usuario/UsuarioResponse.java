package com.Challenge.Foro.dto.usuario;

import com.Challenge.Foro.model.Perfil;
import com.Challenge.Foro.model.Usuario;

public class UsuarioResponse {

    private String id;
    private String nombre;
    private String correoElectronico;
    private String perfil; // Se devolverá el nombre del Enum Perfil como String

    public UsuarioResponse() {
    }

    public UsuarioResponse(String id, String nombre, String correoElectronico, String perfil) {
        this.id = id;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.perfil = perfil;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public static UsuarioResponse fromEntity(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getCorreoElectronico(),
            usuario.getPerfil() != null ? usuario.getPerfil().name() : null
        );
    }
} 