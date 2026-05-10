package org.learning.sistemacanchas.annotations.duracionTurnoValida;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class DuracionTurnoValidaValidator implements ConstraintValidator<DuracionTurnoValida, Long> {
    private final Long[] duracionesValidas = {30L, 60L};

    @Override
    public boolean isValid(Long duracion, ConstraintValidatorContext context) {
        return duracion != null && Arrays.asList(duracionesValidas).contains(duracion);
    }
}