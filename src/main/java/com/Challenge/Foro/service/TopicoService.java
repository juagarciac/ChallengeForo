package com.Challenge.Foro.service;

import com.Challenge.Foro.model.Curso;
import com.Challenge.Foro.model.Topico;
import com.Challenge.Foro.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicoService {

    @Autowired
    private final TopicoRepository topicoRepository;
    

    public TopicoService(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    public void crearTopico(EntradaTopico entradaTopico) {
        var topico = new Topico(entradaTopico.titulo(), entradaTopico.mensaje(), entradaTopico.autor(),new Curso(entradaTopico.curso().nombre(), entradaTopico.curso().categoria();
        topicoRepository.save(topico);
    }
}
