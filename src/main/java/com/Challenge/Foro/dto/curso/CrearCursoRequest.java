package com.Challenge.Foro.dto.curso;

// import javax.validation.constraints.NotBlank;

public class CrearCursoRequest {

    // @NotBlank
    private String nombre;

    // @NotBlank
    private String categoria;

    public CrearCursoRequest() {
    }

    public CrearCursoRequest(String nombre, String categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
} 