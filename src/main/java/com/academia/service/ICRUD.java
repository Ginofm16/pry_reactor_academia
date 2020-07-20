package com.academia.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T, ID> {

    Mono<T> registrar(T p);
    Mono<T> modificar(T p);
    Flux<T> listar();
    Mono<T> listarPorId(ID id);
    Mono<Void> eliminar(ID id);

}
