package org.learning.sistemacanchas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.enums.CanchaEnum;
import org.learning.sistemacanchas.service.CanchaService;
import org.learning.sistemacanchas.service.JwtService;
import org.learning.sistemacanchas.utils.PageDTORes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = CanchaController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CanchaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private CanchaService canchaService;

    private CanchaDTOReq canchaReq;
    private CanchaSummaryDTORes canchaResponseDTO;

    @BeforeEach
    public void init(){
        canchaReq = CanchaDTOReq.builder()
                .nombre("cancha test")
                .tipo(CanchaEnum.FUTBOL)
                .build();

        canchaResponseDTO = CanchaSummaryDTORes.builder()
                .id(1L)
                .nombre(canchaReq.getNombre())
                .tipo(canchaReq.getTipo())
                .creacion(LocalDateTime.now())
                .ultimaActualizacion(LocalDateTime.now())
                .build();
    }

    @Test
    public void canchaController_registrarCancha_devuelveCanchaCreada() throws Exception {
        given(canchaService.registrarCancha(any(CanchaDTOReq.class)))
                .willReturn(canchaResponseDTO);

        ResultActions resultActions = mockMvc.perform(post("/api/canchas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(canchaReq)));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(canchaResponseDTO.getId()));
    }

    @Test
    public void canchaController_traerTodasLasCanchas_devuelvePaginaDeCanchas() throws Exception {
        PageDTORes<CanchaSummaryDTORes> response = PageDTORes.<CanchaSummaryDTORes>builder()
                        .content(List.of(canchaResponseDTO))
                        .pageNo(0)
                        .pageSize(10)
                        .totalElements(1)
                        .totalPages(1)
                        .last(true)
                        .build();

        given(canchaService.traerTodasLasCanchas(0, 10))
                .willReturn(response);

        ResultActions resultActions = mockMvc.perform(get("/api/canchas")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "0")
                .param("pageSize", "10"));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(canchaResponseDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNo").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value("10"));
    }

    @Test
    public void canchaController_traerTodasLasCanchas_devuelvePaginaDeCanchasSinCanchas() throws Exception {
        PageDTORes<CanchaSummaryDTORes> response = PageDTORes.<CanchaSummaryDTORes>builder()
                .content(List.of())
                .pageNo(0)
                .pageSize(10)
                .totalElements(0)
                .totalPages(1)
                .last(true)
                .build();

        given(canchaService.traerTodasLasCanchas(0, 10))
                .willReturn(response);

        ResultActions resultActions = mockMvc.perform(get("/api/canchas")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "0")
                .param("pageSize", "10"));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNo").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value("10"));
    }

    @Test
    public void canchaController_traerCanchaPorId_devuelveCanchaDeseada() throws Exception {
        Long idCancha = 1L;

        given(canchaService.traerCanchaPorId(idCancha))
                .willReturn(canchaResponseDTO);

        ResultActions resultActions = mockMvc.perform(get("/api/canchas/" + idCancha)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(idCancha));
    }
}
