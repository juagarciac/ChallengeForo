package com.Challenge.Foro.controller;


import com.Challenge.Foro.service.EntradaTopico;
import com.Challenge.Foro.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    private final TopicoService topicoService;

    @PostMapping
    public void crearTopico(@RequestBody EntradaTopico topico) {
        topicoService.crearTopico(topico);
    }

    @GetMapping
    public void mostrarTopicos() {

    }

    @PutMapping
    public void actualizarTopico() {

    }

    @DeleteMapping
    public void borrarTopico() {

    }
}
