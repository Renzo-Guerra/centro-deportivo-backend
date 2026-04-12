package org.learning.sistemacanchas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.exception.TurnosSuperpuestosException;
import org.learning.sistemacanchas.service.JwtService;
import org.learning.sistemacanchas.service.TurnoService;
import org.learning.sistemacanchas.utils.PageDTORes;
import org.mockito.InjectMocks;
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

@WebMvcTest(controllers = TurnoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class TurnoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TurnoService turnoService;

    private TurnoDTOReq request;
    private TurnoDTORes responseDTO;

    @BeforeEach
    public void init(){
        request = TurnoDTOReq.builder()
                .nombreCliente("Miguel")
                .apellidoCliente("Suarez")
                .celularCliente("2262-445566")
                .idCancha(1L)
                .inicioTurno(LocalDateTime.now())
                .duracionTurnoMinutos(60L)
                .build();
        responseDTO = TurnoDTORes.builder()
                .id(1L)
                .nombreCliente(request.getNombreCliente())
                .apellidoCliente(request.getApellidoCliente())
                .celularCliente(request.getCelularCliente())
                .creacionTurno(LocalDateTime.now())
                .inicioTurno(request.getInicioTurno())
                .duracionMinutos(request.getDuracionTurnoMinutos())
                .nombreCancha("cancha test")
                .build();
    }

    @Test
    public void turnoController_crearTurno_creaUnNuevoTurno() throws Exception {
        given(turnoService.crearTurno(any(TurnoDTOReq.class)))
                .willReturn(responseDTO);

        ResultActions resultActions = mockMvc.perform(post("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(responseDTO.getId()));
    }

    @Test
    public void turnoController_crearTurno_ArrojaTurnosSuperpuestosException() throws Exception {
        given(turnoService.crearTurno(any(TurnoDTOReq.class)))
                .willThrow(new TurnosSuperpuestosException("Horarios superpuestos con turno existente!"));

        ResultActions resultActions = mockMvc.perform(post("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Horarios superpuestos con turno existente!"));
    }

    @Test
    public void turnoController_traerTodosLosTurnos_devuelvePaginaDeTurnos() throws Exception {
        PageDTORes<TurnoDTORes> responsePage = PageDTORes.<TurnoDTORes>builder()
                .content(List.of(responseDTO))
                .pageNo(0)
                .pageSize(10)
                .totalElements(1)
                .totalPages(1)
                .last(true)
                .build();

        given(turnoService.traerTodosLosTurnos(0, 10))
                .willReturn(responsePage);

        ResultActions resultActions = mockMvc.perform(get("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "0")
                .param("pageSize", "10"));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(responseDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNo").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value("10"));
    }

    @Test
    public void turnoController_traerTodosLosTurnos_devuelvePaginaDeTurnosSinTurnos() throws Exception {
        PageDTORes<TurnoDTORes> responsePage = PageDTORes.<TurnoDTORes>builder()
                .content(List.of())
                .pageNo(0)
                .pageSize(10)
                .totalElements(0)
                .totalPages(1)
                .last(true)
                .build();

        given(turnoService.traerTodosLosTurnos(0, 10))
                .willReturn(responsePage);

        ResultActions resultActions = mockMvc.perform(get("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "0")
                .param("pageSize", "10"));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNo").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value("10"));
    }

}
