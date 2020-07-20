package com.academia.service.impl;


import com.academia.repo.IGenericRepo;
import com.academia.service.ICRUD;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public Mono<T> registrar(T p) {
        return getRepo().save(p);
    }

    @Override
    public Mono<T> modificar(T p) {
        return getRepo().save(p);
    }

    @Override
    public Flux<T> listar() {
        return getRepo().findAll();
    }



    @Override
    public Mono<T> listarPorId(ID id) {
        return getRepo().findById(id);
    }

    @Override
    public Mono<Void> eliminar(ID id) {
        return getRepo().deleteById(id);
    }
}
