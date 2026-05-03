package org.learning.sistemacanchas.repository;

import org.learning.sistemacanchas.entity.Turno;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    @Query("""
        SELECT t.id
        FROM Turno t
        WHERE
          t.cancha.id = :id
          AND t.inicioTurno < :finTurno
          AND t.finTurno > :inicioTurno
    """)
    List<Long> traerTurnosSuperpuestos(@Param("id") Long id,
                                 @Param("inicioTurno") LocalDateTime inicioTurno,
                                 @Param("finTurno") LocalDateTime finTurno);

    @Query("""
        SELECT t
        FROM Turno t
        WHERE t.inicioTurno BETWEEN :inicioDia AND :finDia
    """)
    List<Turno> findAllByFecha(
            @Param("inicioDia") LocalDateTime inicioDia,
            @Param("finDia") LocalDateTime finDia,
            Sort sort);
}
