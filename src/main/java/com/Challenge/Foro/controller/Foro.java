package com.Challenge.Foro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foro")
public class Foro {

    @GetMapping
    public String holaMundo() {
        return "Hola mundo";
    }
}
