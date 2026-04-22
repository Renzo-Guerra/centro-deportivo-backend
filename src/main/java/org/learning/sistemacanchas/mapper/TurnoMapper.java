package org.learning.sistemacanchas.mapper;

import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;

import java.time.temporal.ChronoUnit;

public class TurnoMapper {
    public static TurnoDTORes turnoToTurnoDTORes(Turno entity){
        Cancha cancha = entity.getCancha();

        return TurnoDTORes.builder()
                .id(entity.getId())
                .nombreCliente(entity.getNombreCliente())
                .apellidoCliente(entity.getApellidoCliente())
                .celularCliente(entity.getCelularCliente())
                .inicioTurno(entity.getInicioTurno())
                .creacionTurno(entity.getCreacion())
                .duracionMinutos(ChronoUnit.MINUTES.between(entity.getInicioTurno(), entity.getFinTurno()))
                .idCancha(cancha.getId())
                .nombreCancha(cancha.getNombre())
                .deporte(cancha.getTipo().name())
                .build();
    }
}
