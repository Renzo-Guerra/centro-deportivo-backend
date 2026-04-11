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
import org.learning.sistemacanchas.utils.PageDTORes;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    public void canchaService_traerTodasLasCanchas_traerPaginaDeCanchas(){
        Pageable pageable = PageRequest.of(0, 10);
        List<Cancha> canchas = List.of(cancha);

        Page<Cancha> page = new PageImpl<>(canchas, pageable, canchas.size());

        Mockito.when(canchaRepository.findAll(pageable))
                .thenReturn(page);

        PageDTORes<CanchaSummaryDTORes> response = canchaService.traerTodasLasCanchas(0, 10);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getContent()).isNotEmpty();
        Assertions.assertThat(response.getContent()).hasSize(canchas.size());
        Assertions.assertThat(response.getPageNo()).isEqualTo(page.getNumber());
        Assertions.assertThat(response.getPageSize()).isEqualTo(page.getSize());

        Mockito.verify(canchaRepository, Mockito.times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void canchaService_traerTodasLasCanchas_traerPaginaDeCanchasSinContent(){
        Pageable pageable = PageRequest.of(0, 10);

        Page<Cancha> page = new PageImpl<>(List.of(), pageable, 0);

        Mockito.when(canchaRepository.findAll(pageable))
                .thenReturn(page);

        PageDTORes<CanchaSummaryDTORes> response = canchaService.traerTodasLasCanchas(0, 10);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getContent()).isEmpty();
        Assertions.assertThat(response.getContent()).hasSize(0);
        Assertions.assertThat(response.getPageNo()).isEqualTo(page.getNumber());
        Assertions.assertThat(response.getPageSize()).isEqualTo(page.getSize());

        Mockito.verify(canchaRepository, Mockito.times(1)).findAll(any(Pageable.class));
    }
}
