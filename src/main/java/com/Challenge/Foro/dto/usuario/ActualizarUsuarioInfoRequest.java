package com.Challenge.Foro.dto.usuario;

// import javax.validation.constraints.NotBlank;

public class ActualizarUsuarioInfoRequest {

    // @NotBlank
    private String nombre;

    // @NotBlank // O una validación custom para asegurar que es un valor del Enum Perfil
    private String perfil; // Se espera un String que coincida con los nombres del Enum Perfil

    public ActualizarUsuarioInfoRequest() {
    }

    public ActualizarUsuarioInfoRequest(String nombre, String perfil) {
        this.nombre = nombre;
        this.perfil = perfil;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
} 