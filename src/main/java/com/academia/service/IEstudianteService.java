package com.academia.service;

import com.academia.document.Estudiante;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;

public interface IEstudianteService extends ICRUD<Estudiante, String>{

    public Flux<Estudiante> listarOrdenadoEdad();

}
