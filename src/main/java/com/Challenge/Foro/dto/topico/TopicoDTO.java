package com.Challenge.Foro.dto.topico;

import com.Challenge.Foro.dto.respuesta.RespuestaDTO;
import lombok.Data;
import java.util.List;

public class TopicoDTO {
    @Data
    public static class Crear {
        private String titulo;
        private String mensaje;
        private String autor;
        private String cursoId;
    }

    @Data
    public static class Actualizar {
        private String titulo;
        private String mensaje;
    }

    @Data
    public static class Detalle {
        private String id;
        private String titulo;
        private String mensaje;
        private String autor;
        private String cursoId;
        private List<RespuestaDTO.Detalle> respuestas;
    }

    @Data
    public static class Listado {
        private String id;
        private String titulo;
        private String autor;
        private String cursoNombre;
    }
} 