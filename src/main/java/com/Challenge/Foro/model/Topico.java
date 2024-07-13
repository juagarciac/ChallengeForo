package com.Challenge.Foro.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Entity
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String mensaje;
    @Column(nullable = false)
    private String fechaCreacion;
    @Column()
    private String status;
    @ManyToOne
    private String autor;
    @ManyToOne
    private Curso curso;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Respuesta> respuestas;

    public Topico() {
    }

    public Topico( String titulo, String mensaje, String autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        Instant tiempoEspecifico = Instant.now(); // Ejemplo: 1 de enero de 2023
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define un formato de fecha
        this.fechaCreacion = formateador.format(tiempoEspecifico);;
        this.autor = autor;
        this.curso = curso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }
}
