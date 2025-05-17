package com.Challenge.Foro.dto.respuesta;

import com.Challenge.Foro.model.Respuesta;
import com.Challenge.Foro.model.Topico; // Para obtener el ID del tópico
import com.Challenge.Foro.model.Usuario; // Para obtener el ID y nombre del autor

// Si decides usar LocalDateTime en el DTO, necesitarás parsear la fecha del modelo.
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.time.format.DateTimeParseException;

public class RespuestaResponse {

    private String id;
    private String mensaje;
    private String fechaCreacion; // Manteniendo como String, igual que en el modelo Respuesta.java
    private String topicoId;
    private String usuarioId;
    private String nombreAutor;
    private String solucion;

    public RespuestaResponse() {
    }

    public RespuestaResponse(String id, String mensaje, String fechaCreacion, 
                             String topicoId, String usuarioId, String nombreAutor, String solucion) {
        this.id = id;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.topicoId = topicoId;
        this.usuarioId = usuarioId;
        this.nombreAutor = nombreAutor;
        this.solucion = solucion;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public String getTopicoId() { return topicoId; }
    public void setTopicoId(String topicoId) { this.topicoId = topicoId; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public String getNombreAutor() { return nombreAutor; }
    public void setNombreAutor(String nombreAutor) { this.nombreAutor = nombreAutor; }
    public String getSolucion() { return solucion; }
    public void setSolucion(String solucion) { this.solucion = solucion; }

    public static RespuestaResponse fromEntity(Respuesta respuesta) {
        if (respuesta == null) return null;

        String topicoId = null;
        if (respuesta.getTopico() != null) {
            topicoId = respuesta.getTopico().getId(); // Asume que Topico tiene getId()
        }

        String usuarioId = null;
        String nombreAutor = null;
        if (respuesta.getAutor() != null) {
            usuarioId = respuesta.getAutor().getId(); // Asume que Usuario tiene getId()
            nombreAutor = respuesta.getAutor().getNombre(); // Asume que Usuario tiene getNombre()
        }
        
        // Para fechaCreacion, si quisieras convertirla a LocalDateTime en el DTO:
        // LocalDateTime parsedFechaCreacion = null;
        // if (respuesta.getFechaCreacion() != null) {
        //     try {
        //         // Ajusta el patron si es necesario
        //         parsedFechaCreacion = LocalDateTime.parse(respuesta.getFechaCreacion(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        //     } catch (DateTimeParseException e) {
        //         // Manejar error de parseo, quizás loggear y dejar null, o usar la fecha como String
        //         System.err.println("Error parseando fechaCreacion de Respuesta: " + respuesta.getFechaCreacion());
        //     }
        // }

        return new RespuestaResponse(
            respuesta.getId(),
            respuesta.getMensaje(),
            respuesta.getFechaCreacion(), // Usando String directamente del modelo
            topicoId,
            usuarioId,
            nombreAutor,
            respuesta.getSolucion()
        );
    }
} 