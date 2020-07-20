package com.academia.service;

import com.academia.document.Matricula;
import com.academia.document.MatriculaView;
import reactor.core.publisher.Flux;

public interface IMatriculaService extends ICRUD<Matricula, String> {

    public Flux<MatriculaView> listarMatriculaConEstudiante();

}
