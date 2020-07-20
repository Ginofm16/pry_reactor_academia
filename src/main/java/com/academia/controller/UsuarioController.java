package com.academia.controller;

import com.academia.document.Estudiante;
import com.academia.document.Usuario;
import com.academia.service.IEstudianteService;
import com.academia.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Usuario>>> listar(){
        Flux<Usuario> fxUsuarios =  service.listar();

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fxUsuarios));
    }

    @PostMapping
    public Mono<ResponseEntity<Usuario>> registrar(@Valid @RequestBody Usuario usuario, final ServerHttpRequest req){
        return service.registrar(usuario)
                .map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                );

    }
}
