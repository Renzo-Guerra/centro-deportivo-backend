package org.learning.sistemacanchas.service;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;
import org.learning.sistemacanchas.exception.TurnosSuperpuestosException;
import org.learning.sistemacanchas.mapper.TurnoMapper;
import org.learning.sistemacanchas.repository.TurnoRepository;
import org.learning.sistemacanchas.utils.PageDTORes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoServiceImp implements TurnoService{
    private final TurnoRepository turnoRepository;
    private final CanchaService canchaService;

    @Override
    @Transactional
    public TurnoDTORes crearTurno(TurnoDTOReq turno) {
        // Nos aseguramos de que la cancha exista
        Cancha cancha = canchaService.traerEntidadCanchaPorId(turno.getIdCancha());

        Turno nuevoTurno = Turno.builder()
                .nombreCliente(turno.getNombreCliente())
                .apellidoCliente(turno.getApellidoCliente())
                .celularCliente(turno.getCelularCliente())
                .inicioTurno(turno.getInicioTurno())
                .finTurno(turno.getInicioTurno().plusMinutes(turno.getDuracionTurnoMinutos()))
                .cancha(cancha)
                .build();

        // Verificamos que el horario no se superponga con algun otro turno en esa cancha
        Long cantTurnosSuperpuestos = turnoRepository.traerTurnosSuperpuestos(cancha.getId(), nuevoTurno.getInicioTurno(), nuevoTurno.getFinTurno());

        if(cantTurnosSuperpuestos > 0){
            throw new TurnosSuperpuestosException("Los horarios del nuevo turno se superponen con los horarios de " + cantTurnosSuperpuestos + " turnos!");
        }

        Turno turnoRegistrado = turnoRepository.save(nuevoTurno);

        return TurnoMapper.turnoToTurnoDTORes(turnoRegistrado);
    }

    @Override
    public PageDTORes<TurnoDTORes> traerTodosLosTurnos(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Turno> turnosPage = turnoRepository.findAll(pageable);
        List<Turno> turnosList = turnosPage.getContent();

        List<TurnoDTORes> content = turnosList.stream()
                .map(TurnoMapper::turnoToTurnoDTORes)
                .toList();

        return PageDTORes.<TurnoDTORes>builder()
                .content(content)
                .pageNo(turnosPage.getNumber())
                .pageSize(turnosPage.getSize())
                .totalElements(turnosPage.getTotalElements())
                .totalPages(turnosPage.getTotalPages())
                .last(turnosPage.isLast())
                .build();
    }

    @Override
    public List<TurnoDTORes> traerTurnosPorFecha(LocalDate fecha, String sortBy, String direction) {
        Sort sort = Sort.unsorted();

        if (sortBy != null && !sortBy.isEmpty()) {
            sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        }

        LocalDateTime inicioDia = LocalDateTime.of(fecha, LocalTime.MIN);
        LocalDateTime finDia = LocalDateTime.of(fecha, LocalTime.MAX);

        List<Turno> turnos = turnoRepository.findAllByFecha(inicioDia, finDia, sort);

        return turnos.stream()
                .map(TurnoMapper::turnoToTurnoDTORes)
                .toList();
    }
}
