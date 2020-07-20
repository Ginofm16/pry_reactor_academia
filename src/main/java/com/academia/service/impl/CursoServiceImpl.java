package com.academia.service.impl;

import com.academia.document.Curso;
import com.academia.repo.ICursoRepo;
import com.academia.repo.IGenericRepo;
import com.academia.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoServiceImpl extends CRUDImpl<Curso, String> implements ICursoService {

    @Autowired
    private ICursoRepo repo;

    @Override
    protected IGenericRepo<Curso, String> getRepo() {
        return repo;
    }
}
