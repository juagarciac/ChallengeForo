package com.Challenge.Foro.dto.topico;

// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.Size;

public class ActualizarTopicoRequest {

    // @NotBlank(message = "El título no puede estar vacío")
    // @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    private String titulo;

    // @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    // @NotBlank(message = "El nombre del autor no puede estar vacío")
    private String autor; // Nombre del autor

    // @NotBlank(message = "El ID del curso no puede estar vacío")
    private String cursoId;

    public ActualizarTopicoRequest() {
    }

    public ActualizarTopicoRequest(String titulo, String mensaje, String autor, String cursoId) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.cursoId = cursoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }
} 