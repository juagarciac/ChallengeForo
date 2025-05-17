package com.Challenge.Foro.integration;

import com.Challenge.Foro.dto.usuario.UsuarioDTO;
import com.Challenge.Foro.dto.topico.TopicoDTO;
import com.Challenge.Foro.dto.curso.CursoDTO;
import com.Challenge.Foro.model.Usuario;
import com.Challenge.Foro.model.Curso;
import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.model.Perfil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.Challenge.Foro.repository.UsuarioRepository;
import com.Challenge.Foro.repository.CursoRepository; // Added import
import com.Challenge.Foro.repository.TopicoRepository; // Added import

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ForoIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired // Added Autowired
    private CursoRepository cursoRepository; // Added CursoRepository

    @Autowired // Added Autowired
    private TopicoRepository topicoRepository; // Added TopicoRepository

    @BeforeEach
    public void cleanDatabase() {
        usuarioRepository.deleteAll();
        cursoRepository.deleteAll(); // Added deleteAll for cursoRepository
        topicoRepository.deleteAll(); // Added deleteAll for topicoRepository
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFlujoCompleto() throws Exception {
        // 1. Crear usuario
        UsuarioDTO.Crear usuarioCrearDTO = new UsuarioDTO.Crear();
        usuarioCrearDTO.setNombre("UsuarioDeIntegracion");
        usuarioCrearDTO.setCorreoElectronico("integracion@test.com");
        usuarioCrearDTO.setContrasena("password123");
        usuarioCrearDTO.setPerfil("ESTUDIANTE"); // Perfil es String en DTO

        MvcResult usuarioMvcResult = mockMvc.perform(post("/api/v1/usuarios") // Ruta actualizada
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioCrearDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value(usuarioCrearDTO.getNombre()))
                .andExpect(jsonPath("$.perfil").value(usuarioCrearDTO.getPerfil()))
                .andReturn();

        String usuarioResponseString = usuarioMvcResult.getResponse().getContentAsString();
        UsuarioDTO.Detalle usuarioCreadoDetalle = objectMapper.readValue(usuarioResponseString, UsuarioDTO.Detalle.class);
        String usuarioIdCreado = usuarioCreadoDetalle.getId();
        String nombreUsuarioCreado = usuarioCreadoDetalle.getNombre(); // Usar el nombre del DTO para consistencia

        // 2. Crear curso
        CursoDTO.Crear cursoCrearDTO = new CursoDTO.Crear();
        cursoCrearDTO.setNombre("Curso Integracion");
        cursoCrearDTO.setCategoria("Categoria Integracion");
        MvcResult cursoMvcResult = mockMvc.perform(post("/api/v1/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoCrearDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
        String cursoResponseString = cursoMvcResult.getResponse().getContentAsString();
        CursoDTO.Detalle cursoCreadoDetalle = objectMapper.readValue(cursoResponseString, CursoDTO.Detalle.class);
        String cursoIdCreado = cursoCreadoDetalle.getId();

        // 3. Crear tópico
        TopicoDTO.Crear topicoCrearDTO = new TopicoDTO.Crear();
        topicoCrearDTO.setTitulo("Tópico de Integración");
        topicoCrearDTO.setMensaje("Mensaje del tópico de integración");
        topicoCrearDTO.setAutor(nombreUsuarioCreado);
        topicoCrearDTO.setCursoId(cursoIdCreado);

        MvcResult topicoMvcResult = mockMvc.perform(post("/api/v1/topicos") // Ruta actualizada
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(topicoCrearDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value(topicoCrearDTO.getTitulo()))
                .andReturn();
        
        String topicoResponseString = topicoMvcResult.getResponse().getContentAsString();
        TopicoDTO.Detalle topicoCreadoDetalle = objectMapper.readValue(topicoResponseString, TopicoDTO.Detalle.class);
        String topicoIdCreado = topicoCreadoDetalle.getId();

        // 4. Verificar que el tópico se creó correctamente y se puede obtener
        mockMvc.perform(get("/api/v1/topicos/{id}", topicoIdCreado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(topicoIdCreado))
                .andExpect(jsonPath("$.titulo").value(topicoCrearDTO.getTitulo()))
                .andExpect(jsonPath("$.mensaje").value(topicoCrearDTO.getMensaje()))
                .andExpect(jsonPath("$.autor").value(nombreUsuarioCreado))
                .andExpect(jsonPath("$.cursoId").value(topicoCrearDTO.getCursoId()));
    }
}

