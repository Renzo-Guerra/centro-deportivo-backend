package org.learning.sistemacanchas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.service.TurnoService;
import org.learning.sistemacanchas.utils.PageDTORes;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TurnoController {
    private final TurnoService turnoService;

    @PostMapping("/turnos")
    public ResponseEntity<TurnoDTORes> crearTurno(@Valid @RequestBody TurnoDTOReq request){
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.crearTurno(request));
    }

    @DeleteMapping("/turnos/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable("id") Long id){
        turnoService.eliminarTurno(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/turnos/{id}")
    public ResponseEntity<TurnoDTORes> editarTurno(@PathVariable Long id, @Valid @RequestBody TurnoDTOReq request){
        return ResponseEntity.ok(turnoService.editarTurno(id, request));
    }

    @GetMapping("/turnos")
    public ResponseEntity<PageDTORes<TurnoDTORes>> traerTodosLosTurnos(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        return ResponseEntity.ok(turnoService.traerTodosLosTurnos(pageNo, pageSize));
    }

    @GetMapping("/turnos/fecha")
    public ResponseEntity<List<TurnoDTORes>> traerTurnosPorFecha(
            @RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") String direction
    ) {
        return ResponseEntity.ok(turnoService.traerTurnosPorFecha(fecha, sortBy, direction));
    }

    @GetMapping("/canchas/{id}/turnos")
    public ResponseEntity<PageDTORes<TurnoDTORes>> traerTurnosPorCanchaId(
            @PathVariable Long id,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction){
        return ResponseEntity.ok(turnoService.traerTurnosDeCanchaPaginado(id, pageNo, pageSize, sortBy, direction));
    }
}
