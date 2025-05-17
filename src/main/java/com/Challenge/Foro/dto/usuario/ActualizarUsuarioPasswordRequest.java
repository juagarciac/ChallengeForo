package com.Challenge.Foro.dto.usuario;

// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.Size;

public class ActualizarUsuarioPasswordRequest {

    // @NotBlank
    // @Size(min = 6) // Ejemplo
    private String nuevaContrasena;

    public ActualizarUsuarioPasswordRequest() {
    }

    public ActualizarUsuarioPasswordRequest(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public String getNuevaContrasena() { return nuevaContrasena; }
    public void setNuevaContrasena(String nuevaContrasena) { this.nuevaContrasena = nuevaContrasena; }
} 