package org.learning.sistemacanchas.bootstrap;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.entity.Turno;
import org.learning.sistemacanchas.enums.CanchaEnum;
import org.learning.sistemacanchas.repository.CanchaRepository;
import org.learning.sistemacanchas.repository.TurnoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class TurnoSeeder implements Seeder{
    private final CanchaRepository canchaRepository;
    private final TurnoRepository turnoRepository;

    @Override
    public void seed() {
        List<Cancha> canchas = canchaRepository.findAll();

        String[] nombres = new String[]{"Miguel", "Julieta", "Agustina", "Maria", "Ramiro"};
        String[] apellidos = new String[]{"Torres", "Gonzales", "Ramirez", "Alvares", "Maldonado"};
        String[] celulares = new String[]{"2262-334540", "2262-509887", "2262-316689", "2262-501248", "2262-787822"};

        int CANT_DIAS = 30;
        // HORA_CIERRE SIEMPRE debe ser mayor que HORA_APERTURA.
        // De lo contrario la poblacion podria salir con turnos fuera de horarios laborales.
        LocalTime HORA_APERTURA = LocalTime.of(10, 0);
        LocalTime HORA_CIERRE = LocalTime.of(17, 0);

        LocalDateTime fechaActual = LocalDateTime.of(LocalDate.now(), HORA_APERTURA);

        Collection<Turno> turnos = new HashSet<>();

        // Desde hoy a {CANT_DIAS} dias, cargame turnos en las canchas
        for (int index = 0; index < CANT_DIAS; index++){
            LocalDateTime fechaIteracion = fechaActual.plusDays(index);
            Set<Turno> turnosDia = canchas.stream()
                    .map((cancha -> {
                        Turno t = Turno.builder()
                                .nombreCliente(getRandomValue(nombres))
                                .apellidoCliente(getRandomValue(apellidos))
                                .celularCliente(getRandomValue(celulares))
                                .inicioTurno(LocalDateTime.of(
                                        fechaIteracion.getYear(),
                                        fechaIteracion.getMonth(),
                                        fechaIteracion.getDayOfMonth(),
                                        fechaIteracion.getHour() + getRandomValue(IntStream.rangeClosed(0, (HORA_CIERRE.getHour() - HORA_APERTURA.getHour()) - 1).boxed().toArray(Integer[]::new)),
                                        getInicioMinutos(cancha.getTipo()),
                                        0))
                                // Para que no haya errores en "turnos superpuestos" debemos asegurarnos que los segundos
                                // siempre sean 0 al momento de crearse u editarse
                                .finTurno(LocalDateTime.now())
                                .cancha(cancha)
                                .build();
                        t.setFinTurno(t.getInicioTurno().plusMinutes(getDuracionCancha(cancha.getTipo())));

                        return t;
                    }
                    )).collect(Collectors.toSet());
            turnos.addAll(turnosDia);
        }
        turnoRepository.saveAll(turnos);
    }

    public int getDuracionCancha(CanchaEnum tipo){
        return switch(tipo){
            case FUTBOL, VOLEY -> 60;
            case TENIS, PADEL -> 30;
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
