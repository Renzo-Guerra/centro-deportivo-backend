package org.learning.sistemacanchas.bootstrap;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.repository.RolRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RolSeeder rolSeeder;
    private final UsuarioSeeder usuarioSeeder;
    private final CanchaSeeder canchaSeeder;
    private final TurnoSeeder turnoSeeder;
    private final RolRepository rolRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(rolRepository.count() > 0){
            return;
        }

        rolSeeder.seed();
        usuarioSeeder.seed();
        canchaSeeder.seed();
        turnoSeeder.seed();
    }
}
