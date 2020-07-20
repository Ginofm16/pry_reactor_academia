package com.academia.service.impl;

import com.academia.document.Estudiante;
import com.academia.repo.IEstudianteRepo;
import com.academia.repo.IGenericRepo;
import com.academia.service.IEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class EstudianteServiceImpl extends CRUDImpl<Estudiante, String> implements IEstudianteService {

    @Autowired
    private IEstudianteRepo repo;

    @Override
    protected IGenericRepo<Estudiante, String> getRepo() {
        return repo;
    }

    @Override
    public Flux<Estudiante> listarOrdenadoEdad() {
        return repo.findAll(Sort.by("edad").descending());
    }
}
