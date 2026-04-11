package org.learning.sistemacanchas.DTOs;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.learning.sistemacanchas.enums.CanchaEnum;
import org.learning.sistemacanchas.enums.RolEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CanchaDTOReq {
    @NonNull
    private String nombre;
    @NonNull
    @Enumerated(EnumType.STRING)
    private CanchaEnum tipo;
}
