package com.Challenge.Foro.dto.curso;

import com.Challenge.Foro.model.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CursoMapper {
    @Mapping(target = "id", ignore = true)
    Curso toEntity(CursoDTO.Crear dto);

    CursoDTO.Detalle toDetalle(Curso entity);

    CursoDTO.Listado toListado(Curso entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateEntity(@MappingTarget Curso entity, CursoDTO.Actualizar dto);
} 