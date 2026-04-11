package org.learning.sistemacanchas.service;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.mapper.CanchaMapper;
import org.learning.sistemacanchas.repository.CanchaRepository;
import org.springframework.stereotype.Service;

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
}
