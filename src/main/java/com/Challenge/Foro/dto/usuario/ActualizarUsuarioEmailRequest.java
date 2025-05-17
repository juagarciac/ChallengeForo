package com.Challenge.Foro.dto.usuario;

// import javax.validation.constraints.Email;
// import javax.validation.constraints.NotBlank;

public class ActualizarUsuarioEmailRequest {

    // @NotBlank
    // @Email
    private String nuevoCorreo;

    public ActualizarUsuarioEmailRequest() {
    }

    public ActualizarUsuarioEmailRequest(String nuevoCorreo) {
        this.nuevoCorreo = nuevoCorreo;
    }

    public String getNuevoCorreo() { return nuevoCorreo; }
    public void setNuevoCorreo(String nuevoCorreo) { this.nuevoCorreo = nuevoCorreo; }
} 