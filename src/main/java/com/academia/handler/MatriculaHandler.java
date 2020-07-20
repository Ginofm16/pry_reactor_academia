package com.academia.handler;

import com.academia.document.Matricula;
import com.academia.document.MatriculaView;
import com.academia.service.IEstudianteService;
import com.academia.service.IMatriculaService;
import com.academia.validators.RequestValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Log4j2
@Component
public class MatriculaHandler {

    @Autowired
    private IMatriculaService service;

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private RequestValidator validadorGeneral;

    public Mono<ServerResponse> listar(ServerRequest req){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.listar(), Matricula.class);
    }

    public Mono<ServerResponse> listarMatriculaEstudiante(ServerRequest req){

        MatriculaView mw = new MatriculaView();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.listarMatriculaConEstudiante(), MatriculaView.class);

    }

    public Mono<ServerResponse> listarPorId(ServerRequest req){
        String id = req.pathVariable("id");
        return service.listarPorId(id)
                .flatMap(p -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(p))
                )
                .switchIfEmpty(ServerResponse
                        .notFound()
                        .build()
                );
    }

    public Mono<ServerResponse> registrar(ServerRequest req){

        Mono<Matricula> matriculaMono = req.bodyToMono(Matricula.class);
        return matriculaMono
                .flatMap(validadorGeneral::validate)
                .flatMap(service::registrar)
                .flatMap(p-> ServerResponse
                        .created(URI.create(req.uri().toString().concat("/").concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(p)));
    }

    public Mono<ServerResponse> modificar(ServerRequest req){

        Mono<Matricula> facturaMono = req.bodyToMono(Matricula.class);
        return facturaMono
                .flatMap(service::registrar)
                .flatMap(p-> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(p))
                );
    }

    public Mono<ServerResponse> eliminar(ServerRequest req){
        String id = req.pathVariable("id");

        return service.listarPorId(id)
                .flatMap(p-> service.eliminar(p.getId())
                                .then(ServerResponse
                                        .noContent()
                                        .build()
                                )
                ).switchIfEmpty(ServerResponse
                        .notFound()
                        .build()
                );
    }

}
