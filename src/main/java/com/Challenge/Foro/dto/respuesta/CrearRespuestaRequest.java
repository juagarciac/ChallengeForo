package com.Challenge.Foro.dto.respuesta;

// Podrías añadir anotaciones de validación aquí (ej. @NotBlank)

public class CrearRespuestaRequest {

    private String mensaje;
    private String topicoId;
    private String usuarioId; // ID del usuario que crea la respuesta

    // Constructor por defecto
    public CrearRespuestaRequest() {
    }

    // Constructor con todos los campos
    public CrearRespuestaRequest(String mensaje, String topicoId, String usuarioId) {
        this.mensaje = mensaje;
        this.topicoId = topicoId;
        this.usuarioId = usuarioId;
    }

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTopicoId() {
        return topicoId;
    }

    public void setTopicoId(String topicoId) {
        this.topicoId = topicoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
} 