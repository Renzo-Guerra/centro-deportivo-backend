package org.learning.sistemacanchas.bootstrap;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.entity.Cancha;
import org.learning.sistemacanchas.enums.CanchaEnum;
import org.learning.sistemacanchas.repository.CanchaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CanchaSeeder implements Seeder{
    private final CanchaRepository canchaRepository;

    @Override
    public void seed() {
        String[] tipoCancha = new String[]{"FUTBOL", "TENIS", "PADEL", "VOLEY"};
        List<Cancha> canchas = new ArrayList<>();

        for(int i = 0;i < 6;i++){
            Cancha nuevaCancha = Cancha.builder()
                    .nombre("Cancha " + (i + 1)).
                    tipo(CanchaEnum.valueOf(tipoCancha[(i % tipoCancha.length)])).
                    build();

            canchas.add(nuevaCancha);
        }

        canchaRepository.saveAll(canchas);
    }
}
