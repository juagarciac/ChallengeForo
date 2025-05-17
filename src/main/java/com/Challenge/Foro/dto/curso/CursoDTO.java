package com.Challenge.Foro.dto.curso;

import lombok.Data;

public class CursoDTO {
    @Data
    public static class Crear {
        private String nombre;
        private String categoria;
    }

    @Data
    public static class Actualizar {
        private String nombre;
        private String categoria;
    }

    @Data
    public static class Detalle {
        private String id;
        private String nombre;
        private String categoria;
    }

    @Data
    public static class Listado {
        private String id;
        private String nombre;
        private String categoria;
    }
} 