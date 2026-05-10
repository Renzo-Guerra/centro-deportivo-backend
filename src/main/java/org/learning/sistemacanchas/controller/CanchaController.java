package org.learning.sistemacanchas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.service.CanchaService;
import org.learning.sistemacanchas.utils.PageDTORes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CanchaController {
    private final CanchaService canchaService;

    @PostMapping("/canchas")
    public ResponseEntity<CanchaSummaryDTORes> registrarCancha(@Valid @RequestBody CanchaDTOReq request){
        return ResponseEntity.status(HttpStatus.CREATED).body(canchaService.registrarCancha(request));
    }

    @PutMapping("/canchas/{id}")
    public ResponseEntity<CanchaSummaryDTORes> editarCancha(@PathVariable Long id, @Valid @RequestBody CanchaDTOReq request){
        return ResponseEntity.ok(canchaService.editarCancha(id, request));
    }

    @GetMapping("/canchas")
    public ResponseEntity<PageDTORes<CanchaSummaryDTORes>> traerCanchasPaginado(
        @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return ResponseEntity.ok(canchaService.traerCanchasPaginado(pageNo, pageSize));
    }

    @GetMapping("/canchas/all")
    public ResponseEntity<List<CanchaSummaryDTORes>> traerTodasLasCanchas(
            @RequestParam(name = "sortBy", required = false) List<String> sortParams
    ){
        return ResponseEntity.ok(canchaService.traerTodasLasCanchas(sortParams));
    }

    @GetMapping("/canchas/{id}")
    public ResponseEntity<CanchaSummaryDTORes> traerCanchaPorId(@PathVariable Long id){
        return ResponseEntity.ok(canchaService.traerCanchaPorId(id));
    }

    @DeleteMapping("/canchas/{id}")
    public ResponseEntity<Void> eliminarCancha(@PathVariable Long id){
        canchaService.eliminarCancha(id);

        return ResponseEntity.noContent().build();
    }
}
