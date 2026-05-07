package org.learning.sistemacanchas.annotations.InicioTurnoValido;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InicioTurnoValidoValidator.class)
public @interface InicioTurnoValido {
    String message() default "Inicio de turno invalido!";
}
