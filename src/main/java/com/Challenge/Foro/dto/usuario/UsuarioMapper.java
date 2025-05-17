package com.Challenge.Foro.dto.usuario;

import com.Challenge.Foro.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "perfil", target = "perfil")
    Usuario toEntity(UsuarioDTO.Crear dto);

    UsuarioDTO.Detalle toDetalle(Usuario entity);

    UsuarioDTO.Listado toListado(Usuario entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    void updateEntity(@MappingTarget Usuario entity, UsuarioDTO.Actualizar dto);
} 