package org.learning.sistemacanchas.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import org.learning.sistemacanchas.exception.CascadeException;
import org.learning.sistemacanchas.exception.CredencialesInvalidasException;
import org.learning.sistemacanchas.exception.NoEncontradoException;
import org.learning.sistemacanchas.exception.TurnosSuperpuestosException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(NoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleNoEncontradoException(NoEncontradoException exception){
        Map<String, String> errors = new HashMap<>();

        errors.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, String>> handleCredencialesInvalidasException(CredencialesInvalidasException exception){
        Map<String, String> errors = new HashMap<>();

        errors.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException exception){
        Map<String, String> errors = new HashMap<>();

        errors.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        Map<String, String> errors = new HashMap<>();

        errors.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(TurnosSuperpuestosException.class)
    public ResponseEntity<Map<String, String>> handleTurnosSuperpuestosException(TurnosSuperpuestosException exception){
        Map<String, String> errors = new HashMap<>();

        errors.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, String>> handleSignatureException(SignatureException exception){
        Map<String, String> errors = new HashMap<>();

        errors.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException(ExpiredJwtException exception){
        Map<String, String> errors = new HashMap<>();

        errors.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        Map<String, String> errors = new HashMap<>();

        errors.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}

