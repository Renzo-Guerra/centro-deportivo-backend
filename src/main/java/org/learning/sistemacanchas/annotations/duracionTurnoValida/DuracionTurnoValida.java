package org.learning.sistemacanchas.annotations.duracionTurnoValida;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DuracionTurnoValidaValidator.class)
public @interface DuracionTurnoValida {
    String message() default "Duración de turno invalida!";
}
