package org.learning.sistemacanchas.service;

import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.utils.PageDTORes;

public interface CanchaService {
    CanchaSummaryDTORes registrarCancha(CanchaDTOReq request);
    PageDTORes<CanchaSummaryDTORes> traerTodasLasCanchas(int pageNo, int pageSize);
    CanchaSummaryDTORes traerCanchaPorId(Long id);
    Cancha traerEntidadCanchaPorId(Long id);
    void eliminarCancha(Long id);
}
