package org.learning.sistemacanchas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.service.CanchaService;
import org.learning.sistemacanchas.utils.PageDTORes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/canchas")
@RequiredArgsConstructor
public class CanchaController {
    private final CanchaService canchaService;

    @PostMapping
    public ResponseEntity<CanchaSummaryDTORes> registrarCancha(@Valid @RequestBody CanchaDTOReq request){
        return ResponseEntity.status(HttpStatus.CREATED).body(canchaService.registrarCancha(request));
    }

    @GetMapping
    public ResponseEntity<PageDTORes<CanchaSummaryDTORes>> traerTodasLasCanchas(
        @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return ResponseEntity.ok(canchaService.traerTodasLasCanchas(pageNo, pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<CanchaSummaryDTORes> traerCanchaPorId(@PathVariable Long id){
        return ResponseEntity.ok(canchaService.traerCanchaPorId(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarCancha(@PathVariable Long id){
        canchaService.eliminarCancha(id);

        return ResponseEntity.noContent().build();
    }
}
