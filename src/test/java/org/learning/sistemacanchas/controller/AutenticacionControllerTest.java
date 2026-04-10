package org.learning.sistemacanchas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.sistemacanchas.DTOs.LoguearseDTOReq;
import org.learning.sistemacanchas.DTOs.LoguearseDTORes;
import org.learning.sistemacanchas.service.AutenticacionService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AutenticacionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AutenticacionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private AutenticacionService autenticacionService;
    @MockitoBean
    private JwtService jwtService;

    private LoguearseDTOReq loguearseDTOReq;
    private LoguearseDTORes loguearseDTORes;

    @BeforeEach
    public void init(){
        loguearseDTOReq = LoguearseDTOReq.builder()
                .email("test@gmail.com")
                .contrasenia("test123")
                .build();

        loguearseDTORes = LoguearseDTORes.builder()
                .token("jasfqi1311.f1d1ijf98219mfd1.msdl10ldfmaaxc")
                .build();
    }

    @Test
    public void autenticacionController_loguearse_devuelveToken() throws Exception {
        given(autenticacionService.login(any(LoguearseDTOReq.class)))
                .willReturn(loguearseDTORes);

        ResultActions resultActions = mockMvc.perform(post("/api/autenticacion/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loguearseDTOReq)));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(loguearseDTORes.getToken()));
    }
}
