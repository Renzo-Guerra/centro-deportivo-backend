package org.learning.sistemacanchas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.service.TurnoService;
import org.learning.sistemacanchas.utils.PageDTORes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/turnos")
public class TurnoController {
    private final TurnoService turnoService;

    @PostMapping
    public ResponseEntity<TurnoDTORes> crearTurno(@Valid @RequestBody TurnoDTOReq request){
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.crearTurno(request));
    }

    @GetMapping
    public ResponseEntity<PageDTORes<TurnoDTORes>> traerTodosLosTurnos(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        return ResponseEntity.ok(turnoService.traerTodosLosTurnos(pageNo, pageSize));
    }
}
