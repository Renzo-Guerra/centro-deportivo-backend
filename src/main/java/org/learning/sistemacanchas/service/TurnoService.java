package org.learning.sistemacanchas.service;

import jakarta.validation.Valid;
import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.utils.PageDTORes;

import java.time.LocalDate;
import java.util.List;

public interface TurnoService {
    TurnoDTORes crearTurno(TurnoDTOReq turno);
    PageDTORes<TurnoDTORes> traerTodosLosTurnos(int pageNo, int pageSize);
    List<TurnoDTORes> traerTurnosPorFecha(LocalDate fecha, String sortBy, String direction);
    void eliminarTurno(Long id);
    TurnoDTORes editarTurno(Long id, TurnoDTOReq request);
}
