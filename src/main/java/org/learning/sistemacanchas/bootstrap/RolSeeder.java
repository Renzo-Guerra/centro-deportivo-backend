package org.learning.sistemacanchas.bootstrap;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.entity.Rol;
import org.learning.sistemacanchas.enums.RolEnum;
import org.learning.sistemacanchas.repository.RolRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RolSeeder implements Seeder{
    private final RolRepository rolRepository;

    @Override
    public void seed() {
        RolEnum[] roles = new RolEnum[]{ RolEnum.ADMIN };

        Arrays.stream(roles).forEach(rolActual->{
            Optional<Rol> rolOpcional = rolRepository.findByNombre(rolActual);

            rolOpcional.ifPresentOrElse(System.out::println, ()->{
                Rol nuevoRol = Rol.builder()
                        .nombre(rolActual)
                        .creacion(LocalDateTime.now())
                        .build();

                nuevoRol.setUltimaActualizacion(nuevoRol.getCreacion());

                rolRepository.save(nuevoRol);
            });
        });
    }
}
