package com.Challenge.Foro.dto.respuesta;

// Podrías añadir anotaciones de validación aquí (ej. @NotBlank)

public class ActualizarRespuestaRequest {

    private String mensaje;

    // Constructor por defecto
    public ActualizarRespuestaRequest() {
    }

    // Constructor con todos los campos
    public ActualizarRespuestaRequest(String mensaje) {
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
} 