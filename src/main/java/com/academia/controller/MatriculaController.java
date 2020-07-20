package com.academia.controller;

import com.academia.document.Matricula;
import com.academia.document.MatriculaView;
import com.academia.service.IMatriculaService;
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

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private IMatriculaService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<MatriculaView>>> listar(){
        Flux<MatriculaView> fxMatriculas =  service.listarMatriculaConEstudiante();

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fxMatriculas));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Matricula>> listar(@PathVariable("id") String id){
        return service.listarPorId(id)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Matricula>> registrar(@Valid @RequestBody Matricula matricula, final ServerHttpRequest req){
        return service.registrar(matricula)
                .map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                );

    }

    @PutMapping
    public Mono<ResponseEntity<Matricula>> modificar(@Valid @RequestBody Matricula matricula){
        return service.modificar(matricula)
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
