package org.learning.sistemacanchas.bootstrap;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;
import org.learning.sistemacanchas.enums.CanchaEnum;
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

        LocalDateTime fechaActual = LocalDateTime.now();

        List<Turno> turnos = canchas.stream()
                .map((cancha -> {
                    Turno t = Turno.builder()
                            .nombreCliente(getRandomValue(new String[]{"Miguel", "Julieta", "Agustina", "Maria", "Ramiro"}))
                            .apellidoCliente(getRandomValue(new String[]{"Torres", "Gonzales", "Ramirez", "Alvares", "Maldonado"}))
                            .celularCliente("2231-334456")
                            .inicioTurno(LocalDateTime.of(fechaActual.getYear(), fechaActual.getMonth(), fechaActual.getDayOfMonth(), fechaActual.getHour() + getRandomValue(new Integer[]{0, 1, 2, 3, 4}), getInicioMinutos(cancha.getTipo()), fechaActual.getSecond()))
                            .finTurno(LocalDateTime.now())
                            .cancha(cancha)
                            .build();
                    t.setFinTurno(t.getInicioTurno().plusMinutes(getDuracionCancha(cancha.getTipo())));

                    return t;
                }
                )).toList();

        turnoRepository.saveAll(turnos);
    }

    public int getDuracionCancha(CanchaEnum tipo){
        return switch(tipo){
            case FUTBOL, VOLEY -> 59;
            case TENIS, PADEL -> 29;
        };
    }

    public int getInicioMinutos(CanchaEnum tipo){
        return switch(tipo){
            case FUTBOL, VOLEY -> 0;
            case TENIS, PADEL -> getRandomValue(new Integer[]{0,30});
        };
    }

    public <T> T getRandomValue(T[] values){
        return values[(int) (Math.random() * values.length)];
    }
}
