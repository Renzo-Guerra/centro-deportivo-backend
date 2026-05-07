package org.learning.sistemacanchas.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.learning.sistemacanchas.annotations.InicioTurnoValido.InicioTurnoValido;
import org.learning.sistemacanchas.annotations.duracionTurnoValida.DuracionTurnoValida;
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
    @InicioTurnoValido(
            message = "El inicio del turno debe ser 'en punto' o 'y media'!"
    )
    private LocalDateTime inicioTurno;
    @NonNull
    @DuracionTurnoValida(
            message = "La duracion del turno debe ser 30 o 60 minutos!"
    )
    private Long duracionTurnoMinutos;
}
