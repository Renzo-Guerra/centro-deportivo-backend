package org.learning.sistemacanchas.DTOs;

import lombok.*;
import org.learning.sistemacanchas.entity.Cancha;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TurnoDTORes {
    private Long id;
    private String nombreCliente;
    private String apellidoCliente;
    private String celularCliente;
    private LocalDateTime creacionTurno;
    private LocalDateTime inicioTurno;
    private Long duracionMinutos;
    private String nombreCancha;
}
