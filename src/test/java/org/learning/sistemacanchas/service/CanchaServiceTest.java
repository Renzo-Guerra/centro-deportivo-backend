package org.learning.sistemacanchas.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.enums.CanchaEnum;
import org.learning.sistemacanchas.repository.CanchaRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CanchaServiceTest {
    @Mock
    private CanchaRepository canchaRepository;
    @InjectMocks
    private CanchaServiceImp canchaService;

    private CanchaDTOReq nuevaCanchaReq;
    private Cancha cancha;

    @BeforeEach
    public void init(){
        nuevaCanchaReq = CanchaDTOReq.builder()
                .nombre("cancha test")
                .tipo(CanchaEnum.FUTBOL)
                .build();

        cancha = Cancha.builder()
                .id(1L)
                .nombre(nuevaCanchaReq.getNombre())
                .tipo(nuevaCanchaReq.getTipo())
                .creacion(LocalDateTime.now())
                .ultimaActualizacion(LocalDateTime.now())
                .build();
    }

    @Test
    public void canchaService_registrarCancha_registraNuevaCancha(){
        Mockito.when(canchaRepository.save(any(Cancha.class)))
                .thenReturn(cancha);

        CanchaSummaryDTORes response = canchaService.registrarCancha(nuevaCanchaReq);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).isInstanceOf(CanchaSummaryDTORes.class);
    }
}
