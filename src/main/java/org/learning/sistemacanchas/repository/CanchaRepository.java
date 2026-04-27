package org.learning.sistemacanchas.repository;

import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanchaRepository extends JpaRepository<Cancha, Long> {
    @Query("""
        SELECT t
        FROM Turno t
        WHERE t.cancha.id = :id AND t.inicioTurno > CURRENT_TIMESTAMP
    """)
    List<Turno> findFutureTurnos(@Param("id") Long id);
}
