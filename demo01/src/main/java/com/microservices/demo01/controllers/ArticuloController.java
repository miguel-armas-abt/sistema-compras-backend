package com.microservices.demo01.controllers;

import com.microservices.demo01.models.Articulo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "articulos")
public class ArticuloController {

    private final AtomicLong counter = new AtomicLong();
    private static final String template = "Hello %s";

    // @Pathvariable recupera un recurso                : /{recurso}
    // @RequestParam recupera un atributo del recurso   : /recurso?atributo=valor
    @GetMapping
    public Articulo obtenerArticulo(@RequestParam(value = "descripcion", defaultValue = "sin producto") String descripcion) {
        return new Articulo(counter.incrementAndGet(), String.format(template, descripcion));
    }







}
