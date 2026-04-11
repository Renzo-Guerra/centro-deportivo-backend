package org.learning.sistemacanchas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.enums.CanchaEnum;
import org.learning.sistemacanchas.service.CanchaService;
import org.learning.sistemacanchas.service.JwtService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
}
