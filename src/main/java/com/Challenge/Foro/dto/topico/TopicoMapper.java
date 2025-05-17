package com.Challenge.Foro.dto.topico;

import com.Challenge.Foro.dto.respuesta.RespuestaMapper;
import com.Challenge.Foro.model.Topico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {RespuestaMapper.class})
public interface TopicoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "respuestas", ignore = true)
    Topico toEntity(TopicoDTO.Crear dto);

    @Mapping(source = "curso.id", target = "cursoId")
    TopicoDTO.Detalle toDetalle(Topico entity);

    @Mapping(source = "curso.nombre", target = "cursoNombre")
    TopicoDTO.Listado toListado(Topico entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "respuestas", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "curso", ignore = true)
    void updateEntity(@MappingTarget Topico entity, TopicoDTO.Actualizar dto);
} 