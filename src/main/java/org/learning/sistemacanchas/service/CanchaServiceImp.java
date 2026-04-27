package org.learning.sistemacanchas.service;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;
import org.learning.sistemacanchas.exception.CascadeException;
import org.learning.sistemacanchas.exception.NoEncontradoException;
import org.learning.sistemacanchas.mapper.CanchaMapper;
import org.learning.sistemacanchas.repository.CanchaRepository;
import org.learning.sistemacanchas.utils.PageDTORes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CanchaServiceImp implements CanchaService{
    private final CanchaRepository canchaRepository;

    @Override
    public CanchaSummaryDTORes registrarCancha(CanchaDTOReq request) {
        Cancha canchaReq = CanchaMapper.canchaDTOReqToCancha(request);

        Cancha savedCancha = canchaRepository.save(canchaReq);

        return CanchaMapper.canchaToCanchaSummaryDTORes(savedCancha);
    }

    @Override
    public PageDTORes<CanchaSummaryDTORes> traerTodasLasCanchas(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Cancha> canchasPage = canchaRepository.findAll(pageable);
        List<Cancha> canchasList = canchasPage.getContent();

        List<CanchaSummaryDTORes> content = canchasList.stream()
                .map(CanchaMapper::canchaToCanchaSummaryDTORes)
                .toList();

        return PageDTORes.<CanchaSummaryDTORes>builder()
                .content(content)
                .pageNo(canchasPage.getNumber())
                .pageSize(canchasPage.getSize())
                .totalElements(canchasPage.getTotalElements())
                .totalPages(canchasPage.getTotalPages())
                .last(canchasPage.isLast())
                .build();
    }

    @Override
    public CanchaSummaryDTORes traerCanchaPorId(Long id) {
        Cancha cancha = this.traerEntidadCanchaPorId(id);

        return CanchaMapper.canchaToCanchaSummaryDTORes(cancha);
    }

    @Override
    public Cancha traerEntidadCanchaPorId(Long id) {
        return canchaRepository.findById(id)
                .orElseThrow(()-> new NoEncontradoException("No se logró encontrar la cancha con id '" + id + "'!"));
    }

    @Override
    public void eliminarCancha(Long id) {
        Cancha cancha = traerEntidadCanchaPorId(id);

        List<Turno> turnos = canchaRepository.findFutureTurnos(cancha.getId());

        if(!turnos.isEmpty()){
            throw new CascadeException("La cancha no puede ser eliminada ya que tiene turnos asignados para el futuro!");
        }

        canchaRepository.delete(cancha);
    }

}
