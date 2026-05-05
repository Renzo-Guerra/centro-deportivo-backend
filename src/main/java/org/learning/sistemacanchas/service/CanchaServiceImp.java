package org.learning.sistemacanchas.service;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;
import org.learning.sistemacanchas.exception.NoEncontradoException;
import org.learning.sistemacanchas.exception.TurnosSuperpuestosException;
import org.learning.sistemacanchas.mapper.CanchaMapper;
import org.learning.sistemacanchas.repository.CanchaRepository;
import org.learning.sistemacanchas.utils.PageDTORes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CanchaServiceImp implements CanchaService{
    private final CanchaRepository canchaRepository;

    @Transactional
    @Override
    public CanchaSummaryDTORes registrarCancha(CanchaDTOReq request) {
        Cancha canchaReq = CanchaMapper.canchaDTOReqToCancha(request);

        Cancha savedCancha = canchaRepository.save(canchaReq);

        return CanchaMapper.canchaToCanchaSummaryDTORes(savedCancha);
    }

    @Override
    @Transactional
    public PageDTORes<CanchaSummaryDTORes> traerCanchasPaginado(int pageNo, int pageSize) {
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
    @Transactional
    public CanchaSummaryDTORes traerCanchaPorId(Long id) {
        Cancha cancha = this.traerEntidadCanchaPorId(id);

        return CanchaMapper.canchaToCanchaSummaryDTORes(cancha);
    }

    @Override
    @Transactional
    public Cancha traerEntidadCanchaPorId(Long id) {
        return canchaRepository.findById(id)
                .orElseThrow(()-> new NoEncontradoException("No se logró encontrar la cancha con id '" + id + "'!"));
    }

    @Transactional
    @Override
    public void eliminarCancha(Long id) {
        Cancha cancha = traerEntidadCanchaPorId(id);

        verificarSiCanchaTieneTurnosFuturos(cancha.getId());

        canchaRepository.delete(cancha);
    }

    @Transactional
    @Override
    public CanchaSummaryDTORes editarCancha(Long id, CanchaDTOReq request) {
        Cancha cancha = traerEntidadCanchaPorId(id);

        verificarSiCanchaTieneTurnosFuturos(cancha.getId());

        cancha.setNombre(request.getNombre());
        cancha.setTipo(request.getTipo());

        Cancha editedCancha = canchaRepository.save(cancha);

        return CanchaMapper.canchaToCanchaSummaryDTORes(editedCancha);
    }

    @Override
    @Transactional
    public List<CanchaSummaryDTORes> traerTodasLasCanchas() {
        List<Cancha> canchas = canchaRepository.findAll();

        return canchas.stream()
                .map(CanchaMapper::canchaToCanchaSummaryDTORes)
                .toList();
    }

    /**
     * Dado un idCancha, verifica si la cancha tiene turnos futuros pendientes.
     * <p>
     * Evalua a partir de "inicioTurno", si un turno ya comenzó no se contará
     * como un turno del futuro.
     * </p>
     *
     * @param idCancha Cancha a evaluar
     * @throws TurnosSuperpuestosException Si se encuentran turnos programados después de la fecha/hora actual.
     */
    @Transactional
    private void verificarSiCanchaTieneTurnosFuturos (Long idCancha){
        List<Turno> turnos = canchaRepository.findFutureTurnos(idCancha);

        if(!turnos.isEmpty()){
            throw new TurnosSuperpuestosException("La cancha tiene turnos asignados para el futuro!");
        }
    }
}
