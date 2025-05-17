package com.Challenge.Foro.dto.respuesta;

import lombok.Data;

public class RespuestaDTO {
    @Data
    public static class Crear {
        private String mensaje;
        private String usuarioId;
        private String topicoId;
    }

    @Data
    public static class Actualizar {
        private String mensaje;
    }

    @Data
    public static class Detalle {
        private String id;
        private String mensaje;
        private String autor;
        private String topicoId;
    }

    @Data
    public static class Listado {
        private String id;
        private String mensaje;
        private String autor;
    }
} 