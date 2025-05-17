package com.Challenge.Foro.dto.usuario;

import lombok.Data;

public class UsuarioDTO {
    @Data
    public static class Crear {
        private String nombre;
        private String correoElectronico;
        private String contrasena;
        private String perfil;
    }

    @Data
    public static class Actualizar {
        private String nombre;
        private String correoElectronico;
        private String perfil;
    }

    @Data
    public static class Detalle {
        private String id;
        private String nombre;
        private String correoElectronico;
        private String perfil;
    }

    @Data
    public static class Listado {
        private String id;
        private String nombre;
        private String perfil;
    }
} 