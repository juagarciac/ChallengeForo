package com.Challenge.Foro.dto.topico;

import com.Challenge.Foro.model.Curso; // Necesario para el nombre del curso
import com.Challenge.Foro.model.Topico;
import java.time.LocalDateTime;

public class TopicoResponse {

    private String id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String status;
    private String autor; // Nombre o identificador del autor
    private String cursoId;
    private String nombreCurso;
    // No incluiremos la lista de respuestas aquí para evitar ciclos y mantener el DTO enfocado.
    // Las respuestas se obtendrán a través de su propio endpoint.

    public TopicoResponse() {
    }

    public TopicoResponse(String id, String titulo, String mensaje, LocalDateTime fechaCreacion, 
                          String status, String autor, String cursoId, String nombreCurso) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.status = status;
        this.autor = autor;
        this.cursoId = cursoId;
        this.nombreCurso = nombreCurso;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getCursoId() { return cursoId; }
    public void setCursoId(String cursoId) { this.cursoId = cursoId; }
    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

    // Método estático de fábrica para convertir una entidad Topico a TopicoResponse
    public static TopicoResponse fromEntity(Topico topico) {
        if (topico == null) return null;
        
        String cursoId = null;
        String nombreCurso = null;
        if (topico.getCurso() != null) {
            cursoId = topico.getCurso().getId(); // Asume que Curso tiene getId()
            nombreCurso = topico.getCurso().getNombre(); // Asume que Curso tiene getNombre()
        }

        return new TopicoResponse(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getStatus(),
            topico.getAutor(), // El autor es un String en el modelo Topico
            cursoId,
            nombreCurso
        );
    }
} 