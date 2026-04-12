package org.learning.sistemacanchas.service;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;
import org.learning.sistemacanchas.exception.TurnosSuperpuestosException;
import org.learning.sistemacanchas.mapper.TurnoMapper;
import org.learning.sistemacanchas.repository.TurnoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TurnoServiceImp implements TurnoService{
    private final TurnoRepository turnoRepository;
    private final CanchaService canchaService;

    @Override
    @Transactional
    public TurnoDTORes crearTurno(TurnoDTOReq turno) {
        // Nos aseguramos de que la cancha exista
        Cancha cancha = canchaService.traerEntidadCanchaPorId(turno.getIdCancha());

        Turno nuevoTurno = Turno.builder()
                .nombreCliente(turno.getNombreCliente())
                .apellidoCliente(turno.getApellidoCliente())
                .celularCliente(turno.getCelularCliente())
                .inicioTurno(turno.getInicioTurno())
                .finTurno(turno.getInicioTurno().plusMinutes(turno.getDuracionTurnoMinutos()))
                .cancha(cancha)
                .build();

        // Verificamos que el horario no se superponga con algun otro turno en esa cancha
        Long cantTurnosSuperpuestos = turnoRepository.traerTurnosSuperpuestos(cancha.getId(), nuevoTurno.getInicioTurno(), nuevoTurno.getFinTurno());

        if(cantTurnosSuperpuestos > 0){
            throw new TurnosSuperpuestosException("Los horarios del nuevo turno se superponen con los horarios de " + cantTurnosSuperpuestos + " turnos!");
        }

        Turno turnoRegistrado = turnoRepository.save(nuevoTurno);

        return TurnoMapper.turnoToTurnoDTORes(turnoRegistrado);
    }
}
