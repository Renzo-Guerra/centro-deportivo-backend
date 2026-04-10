package org.learning.sistemacanchas.bootstrap;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.UsuarioDTOReq;
import org.learning.sistemacanchas.enums.RolEnum;
import org.learning.sistemacanchas.service.AutenticacionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioSeeder implements Seeder{
    private final AutenticacionService autenticacionService;

    @Override
    public void seed() {
        this.cargarAdmins();
    }

    private void cargarAdmins(){
        UsuarioDTOReq admin1 = UsuarioDTOReq.builder()
                .nombre("Ricardo")
                .apellido("Fontana")
                .email("riquifontana@gmail.com")
                .contrasenia("password123")
                .build();

        List<UsuarioDTOReq> admins = List.of(admin1);

        admins.forEach(admin->{
            autenticacionService.registrarse(admin, RolEnum.ADMIN);
        });
    }

}
