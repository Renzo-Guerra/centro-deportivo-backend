package org.learning.sistemacanchas.annotations.InicioTurnoValido;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.util.Arrays;

public class InicioTurnoValidoValidator implements ConstraintValidator<InicioTurnoValido , LocalDateTime> {
    private final int[] validMinutes = {0, 30};

    @Override
    public boolean isValid(LocalDateTime inicioTurno, ConstraintValidatorContext constraintValidatorContext) {
        return
                inicioTurno != null &&
                Arrays.stream(validMinutes)
                        .anyMatch(current -> current == inicioTurno.getMinute());
    }
}
