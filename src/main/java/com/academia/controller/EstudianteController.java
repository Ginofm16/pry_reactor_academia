package com.academia.controller;

import com.academia.document.Estudiante;
import com.academia.service.IEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private IEstudianteService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Estudiante>>> listar(){
        Flux<Estudiante> fxEstudiantes =  service.listarOrdenadoEdad();

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fxEstudiantes));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Estudiante>> listar(@PathVariable("id") String id){
        return service.listarPorId(id)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Estudiante>> registrar(@Valid @RequestBody Estudiante estudiante, final ServerHttpRequest req){
        return service.registrar(estudiante)
                .map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                );

    }

    @PutMapping
    public Mono<ResponseEntity<Estudiante>> modificar(@Valid @RequestBody Estudiante estudiante){
        return service.modificar(estudiante)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                );
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id){

        return service.listarPorId(id)
                .flatMap(p -> {
                    return service.eliminar(p.getId())
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                })
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
