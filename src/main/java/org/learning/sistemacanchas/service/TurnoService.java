package org.learning.sistemacanchas.service;

import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.utils.PageDTORes;

public interface TurnoService {
    TurnoDTORes crearTurno(TurnoDTOReq turno);
    PageDTORes<TurnoDTORes> traerTodosLosTurnos(int pageNo, int pageSize);
}
