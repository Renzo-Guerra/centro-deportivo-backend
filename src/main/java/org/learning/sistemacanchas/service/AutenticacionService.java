package org.learning.sistemacanchas.service;

import org.learning.sistemacanchas.DTOs.LoguearseDTOReq;
import org.learning.sistemacanchas.DTOs.LoguearseDTORes;
import org.learning.sistemacanchas.DTOs.UsuarioDTOReq;
import org.learning.sistemacanchas.DTOs.UsuarioDTORes;
import org.learning.sistemacanchas.enums.RolEnum;

public interface AutenticacionService {
    UsuarioDTORes registrarse(UsuarioDTOReq request, RolEnum rolEnum);
    LoguearseDTORes login(LoguearseDTOReq request);
}
