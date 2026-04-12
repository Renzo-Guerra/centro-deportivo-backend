package org.learning.sistemacanchas.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;
import org.learning.sistemacanchas.enums.CanchaEnum;
import org.learning.sistemacanchas.repository.CanchaRepository;
import org.learning.sistemacanchas.repository.TurnoRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TurnoServiceTest {
    @Mock
    private TurnoRepository turnoRepository;
    @Mock
    private CanchaServiceImp canchaService;
    @InjectMocks
    private TurnoServiceImp turnoService;

    private Cancha cancha;
    private TurnoDTOReq turnoReq;
    private Turno turno;

    @BeforeEach
    public void init(){
        cancha = Cancha.builder()
                .id(1L)
                .nombre("cancha test")
                .tipo(CanchaEnum.FUTBOL)
                .build();

        turnoReq = TurnoDTOReq.builder()
                .nombreCliente("Miguel")
                .apellidoCliente("Suarez")
                .celularCliente("2262-445566")
                .idCancha(cancha.getId())
                .inicioTurno(LocalDateTime.now())
                .duracionTurnoMinutos(60L)
                .build();

        turno = Turno.builder()
                .id(1L)
                .nombreCliente("Miguel")
                .apellidoCliente("Suarez")
                .celularCliente("2262-445566")
                .inicioTurno(turnoReq.getInicioTurno())
                .finTurno(turnoReq.getInicioTurno().plusMinutes(turnoReq.getDuracionTurnoMinutos()))
                .cancha(cancha)
                .build();
    }

    @Test
    public void turnoService_crearTurno_registraNuevoTurno(){
        when(canchaService.traerEntidadCanchaPorId(cancha.getId()))
                .thenReturn(cancha);
        when(turnoRepository.save(any(Turno.class)))
                .thenReturn(turno);

        TurnoDTORes response = turnoService.crearTurno(turnoReq);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(turno.getId());
        Assertions.assertThat(response.getNombreCliente()).isEqualTo(turno.getNombreCliente());
        Assertions.assertThat(response.getNombreCancha()).isEqualTo(turno.getCancha().getNombre());

        Mockito.verify(canchaService, times(1)).traerEntidadCanchaPorId(cancha.getId());
        Mockito.verify(turnoRepository, times(1)).save(any(Turno.class));
    }
}
