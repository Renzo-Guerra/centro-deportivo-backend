package org.learning.sistemacanchas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.LoguearseDTOReq;
import org.learning.sistemacanchas.DTOs.LoguearseDTORes;
import org.learning.sistemacanchas.service.AutenticacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/autenticacion")
public class AutenticacionController {
    private final AutenticacionService autenticacionService;

    @PostMapping("/login")
    public ResponseEntity<LoguearseDTORes> login(@Valid @RequestBody LoguearseDTOReq request){
        return ResponseEntity.ok(autenticacionService.login(request));
    }
}
