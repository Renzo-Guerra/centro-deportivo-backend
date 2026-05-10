package org.learning.sistemacanchas.service;

import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.utils.PageDTORes;

import java.util.List;

public interface CanchaService {
    CanchaSummaryDTORes registrarCancha(CanchaDTOReq request);
    PageDTORes<CanchaSummaryDTORes> traerCanchasPaginado(int pageNo, int pageSize);
    CanchaSummaryDTORes traerCanchaPorId(Long id);
    Cancha traerEntidadCanchaPorId(Long id);
    void eliminarCancha(Long id);
    CanchaSummaryDTORes editarCancha(Long id, CanchaDTOReq request);
    List<CanchaSummaryDTORes> traerTodasLasCanchas(List<String> sortParams);
}
