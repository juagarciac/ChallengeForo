package com.Challenge.Foro.dto.respuesta;

import com.Challenge.Foro.model.Respuesta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RespuestaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "topico", ignore = true)
    Respuesta toEntity(RespuestaDTO.Crear dto);

    @Mapping(source = "autor.nombre", target = "autor")
    @Mapping(source = "topico.id", target = "topicoId")
    RespuestaDTO.Detalle toDetalle(Respuesta entity);

    @Mapping(source = "autor.nombre", target = "autor")
    RespuestaDTO.Listado toListado(Respuesta entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "topico", ignore = true)
    void updateEntity(@MappingTarget Respuesta entity, RespuestaDTO.Actualizar dto);
} 