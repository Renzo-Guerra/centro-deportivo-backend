package org.learning.sistemacanchas.repository;

import org.learning.sistemacanchas.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    @Query("""
        SELECT COUNT(t)
        FROM Turno t
        WHERE
          t.cancha.id = :id
          AND t.inicioTurno < :finTurno
          AND t.finTurno > :inicioTurno
    """)
    Long traerTurnosSuperpuestos(@Param("id") Long id,
                                 @Param("inicioTurno") LocalDateTime inicioTurno,
                                 @Param("finTurno") LocalDateTime finTurno);
}
