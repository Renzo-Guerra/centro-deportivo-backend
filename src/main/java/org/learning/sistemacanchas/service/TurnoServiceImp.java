package org.learning.sistemacanchas.service;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.TurnoDTOReq;
import org.learning.sistemacanchas.DTOs.TurnoDTORes;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;
import org.learning.sistemacanchas.exception.NoEncontradoException;
import org.learning.sistemacanchas.exception.TurnosSuperpuestosException;
import org.learning.sistemacanchas.mapper.CanchaMapper;
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
        List<Long> idsTurnosSuperpuestos = turnoRepository.traerTurnosSuperpuestos(cancha.getId(), nuevoTurno.getInicioTurno(), nuevoTurno.getFinTurno());

        if(!idsTurnosSuperpuestos.isEmpty()){
            throw new TurnosSuperpuestosException("El nuevo turno se superpone con los turnos " + idsTurnosSuperpuestos + "!");
        }

        Turno turnoRegistrado = turnoRepository.save(nuevoTurno);

        return TurnoMapper.turnoToTurnoDTORes(turnoRegistrado);
    }

    @Override
    @Transactional
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
    @Transactional
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

    @Transactional
    private Turno traerEntidadTurnoPorId(Long id){
        return turnoRepository.findById(id)
                .orElseThrow(()-> new NoEncontradoException("No se logró encontrar el turno con id '" + id + "'!"));
    }

    @Override
    @Transactional
    public void eliminarTurno(Long id) {
        Turno turno = this.traerEntidadTurnoPorId(id);

        turnoRepository.delete(turno);
    }

    @Override
    @Transactional
    public TurnoDTORes editarTurno(Long id, TurnoDTOReq request) {
        Turno turno = traerEntidadTurnoPorId(id);

        turno.setNombreCliente(request.getNombreCliente());
        turno.setApellidoCliente(request.getApellidoCliente());
        turno.setCelularCliente(request.getCelularCliente());
        turno.setInicioTurno(request.getInicioTurno());
        turno.setFinTurno(request.getInicioTurno().plusMinutes(request.getDuracionTurnoMinutos()));

        // Verificamos que el horario no se superponga con algun otro turno en esa cancha
        List<Long> idsTurnosSuperpuestos = turnoRepository.traerTurnosSuperpuestos(id, turno.getInicioTurno(), turno.getFinTurno());

        // En caso de que el usuario edite la duracion del turno, traerTurnosSuperpuestos va a traer el mismo
        // turno que estamos tratando de editar como una superposicion de turnos, en caso de que ese sea el
        // único turno superpuesto, debemos dejar pasar el edit. Caso contrario quiere decir que el turno
        // superpuesto es diferente al que estamos tratando de editar.
        if(idsTurnosSuperpuestos.size() == 1 && !idsTurnosSuperpuestos.getFirst().equals(turno.getId())){
            throw new TurnosSuperpuestosException("El nuevo horario del turno se superpone con los horarios de los turnos " + idsTurnosSuperpuestos + "!");
        }

        Turno editedTurno = turnoRepository.save(turno);

        return TurnoMapper.turnoToTurnoDTORes(editedTurno);
    }

    @Transactional
    public PageDTORes<TurnoDTORes> traerTurnosDeCanchaPaginado(Long id, int pageNo, int pageSize, String sortBy, String direction) {
        Sort sort = Sort.unsorted();

        if (sortBy != null && !sortBy.isEmpty()) {
            sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Turno> turnosPage = turnoRepository.findTurnosByCanchaId(id, pageable);
        List<Turno> turnoList = turnosPage.getContent();

        List<TurnoDTORes> content = turnoList.stream()
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


}
