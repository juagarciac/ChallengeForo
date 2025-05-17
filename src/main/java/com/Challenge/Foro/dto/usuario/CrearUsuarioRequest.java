package com.Challenge.Foro.dto.usuario;

// import javax.validation.constraints.Email;
// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.Size;

public class CrearUsuarioRequest {

    // @NotBlank
    private String nombre;

    // @NotBlank
    // @Email
    private String correoElectronico;

    // @NotBlank
    // @Size(min = 6) // Ejemplo de validación de contraseña
    private String contrasena;

    // @NotBlank // O una validación custom para asegurar que es un valor del Enum Perfil
    private String perfil; // Se espera un String que coincida con los nombres del Enum Perfil

    public CrearUsuarioRequest() {
    }

    public CrearUsuarioRequest(String nombre, String correoElectronico, String contrasena, String perfil) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.perfil = perfil;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
} 