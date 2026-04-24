package org.learning.sistemacanchas.bootstrap;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;
import org.learning.sistemacanchas.repository.CanchaRepository;
import org.learning.sistemacanchas.repository.TurnoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TurnoSeeder implements Seeder{
    private final CanchaRepository canchaRepository;
    private final TurnoRepository turnoRepository;

    @Override
    public void seed() {
        List<Cancha> canchas = canchaRepository.findAll();

        List<Turno> turnos = canchas.stream()
                .map((cancha -> {
                    Turno t = Turno.builder()
                            .nombreCliente(getRandomValue(new String[]{"Miguel", "Julieta", "Agustina", "Maria", "Ramiro"}))
                            .apellidoCliente(getRandomValue(new String[]{"Torres", "Gonzales", "Ramirez", "Alvares", "Maldonado"}))
                            .celularCliente("2231-334456")
                            .inicioTurno(LocalDateTime.now().plusHours(getRandomValue(new Integer[]{1, 2, 3, 4, 5, 6})))
                            .finTurno(LocalDateTime.now())
                            .cancha(cancha)
                            .build();
                    t.setFinTurno(t.getInicioTurno().plusMinutes(getRandomValue(new Integer[]{60, 30, 45})));

                    return t;
                }
                )).toList();

        turnoRepository.saveAll(turnos);
    }

    public <T> T getRandomValue(T[] values){
        return values[(int) (Math.random() * values.length)];
    }
}
