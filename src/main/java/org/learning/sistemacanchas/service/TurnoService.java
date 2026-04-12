package org.learning.sistemacanchas.service;

import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;

public interface TurnoService {
    TurnoDTORes crearTurno(TurnoDTOReq turno);
}
