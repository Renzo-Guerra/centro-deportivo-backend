package org.learning.sistemacanchas.bootstrap;

import lombok.RequiredArgsConstructor;
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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        rolSeeder.seed();
        usuarioSeeder.seed();
        canchaSeeder.seed();
        turnoSeeder.seed();
    }
}
