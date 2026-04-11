package org.learning.sistemacanchas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.service.CanchaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/canchas")
@RequiredArgsConstructor
public class CanchaController {
    private final CanchaService canchaService;

    @PostMapping
    public ResponseEntity<CanchaSummaryDTORes> registrarCancha(@Valid @RequestBody CanchaDTOReq request){
        return ResponseEntity.status(HttpStatus.CREATED).body(canchaService.registrarCancha(request));
    }
}
