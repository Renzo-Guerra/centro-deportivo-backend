package org.learning.sistemacanchas.DTOs;

import lombok.*;
import org.learning.sistemacanchas.enums.CanchaEnum;
import org.learning.sistemacanchas.enums.RolEnum;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CanchaSummaryDTORes {
    private Long id;
    private String nombre;
    private CanchaEnum tipo;
    private LocalDateTime creacion;
    private LocalDateTime ultimaActualizacion;
}
