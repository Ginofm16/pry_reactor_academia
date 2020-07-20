package com.academia.repo;

import com.academia.document.Estudiante;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;

public interface IEstudianteRepo extends IGenericRepo<Estudiante, String> {


}
