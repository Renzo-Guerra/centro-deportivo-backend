package org.learning.sistemacanchas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.exception.TurnosSuperpuestosException;
import org.learning.sistemacanchas.service.JwtService;
import org.learning.sistemacanchas.service.TurnoService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    private TurnoDTORes response;

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
        response = TurnoDTORes.builder()
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
                .willReturn(response);

        ResultActions resultActions = mockMvc.perform(post("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()));
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

}
