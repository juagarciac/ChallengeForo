package com.Challenge.Foro.dto.curso;

// import javax.validation.constraints.NotBlank;

public class ActualizarCursoNombreRequest {

    // @NotBlank
    private String nuevoNombre;

    public ActualizarCursoNombreRequest() {
    }

    public ActualizarCursoNombreRequest(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoNombre() { return nuevoNombre; }
    public void setNuevoNombre(String nuevoNombre) { this.nuevoNombre = nuevoNombre; }
} 