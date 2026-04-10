package org.learning.sistemacanchas.DTOs;

import lombok.*;
import org.learning.sistemacanchas.entity.Rol;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTORes {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDateTime creacion;
    private LocalDateTime ultimaActualizacion;
    private Rol rol;
}
