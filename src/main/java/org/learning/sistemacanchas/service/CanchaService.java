package org.learning.sistemacanchas.service;

import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;

public interface CanchaService {
    CanchaSummaryDTORes registrarCancha(CanchaDTOReq request);
}
