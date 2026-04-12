package org.learning.sistemacanchas.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.learning.sistemacanchas.entity.Cancha;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TurnoDTOReq {
    @NonNull
    private String nombreCliente;
    @NonNull
    private String apellidoCliente;
    @NonNull
    private String celularCliente;
    @NonNull
    private Long idCancha;
    @NonNull
    private LocalDateTime inicioTurno;
    @NonNull
    @Positive
    private Long duracionTurnoMinutos;
}
