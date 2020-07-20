package com.academia.service.impl;

import com.academia.document.Matricula;
import com.academia.document.MatriculaView;
import com.academia.repo.IEstudianteRepo;
import com.academia.repo.IGenericRepo;
import com.academia.repo.IMatriculaRepo;
import com.academia.service.IEstudianteService;
import com.academia.service.IMatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MatriculaServiceImpl extends CRUDImpl<Matricula, String> implements IMatriculaService {

    @Autowired
    private IMatriculaRepo repo;

    @Autowired
    private IEstudianteService repoEst;

    @Override
    protected IGenericRepo<Matricula, String> getRepo() {
        return repo;
    }

    @Override
    public Flux<MatriculaView> listarMatriculaConEstudiante(){

        MatriculaView mw = new MatriculaView();

        return repo.findAll()
                .flatMap(ma -> {
                    return Mono.just(ma)
                            .zipWith(repoEst.listarPorId(ma.getEstudiante().getId()), (mat, est) ->{
                                mat.setEstudiante(est);
                                mw.setId(mat.getId());
                                mw.setFecha(mat.getFecha());
                                mw.setEstudiante(mat.getEstudiante());
                                mw.setCursos(mat.getCursos());
                                mw.setEstudiante(mat.getEstudiante());
                                mw.setEstado(mat.getEstado());
                                return mw;
                            });
                });

    }

}
