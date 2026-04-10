package org.learning.sistemacanchas.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.sistemacanchas.entity.Rol;
import org.learning.sistemacanchas.enums.RolEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@DataJpaTest
public class RolRepositoryTest {
    @Autowired
    private RolRepository rolRepository;

    private Rol rol;

    @BeforeEach
    public void init(){
        rol = Rol.builder()
                .nombre(RolEnum.ADMIN)
                .build();
    }

    @Test
    public void RolRepository_save_guardaNuevaEntidad(){
        Rol rolGuardado = rolRepository.save(rol);

        Assertions.assertThat(rolGuardado).isNotNull();
        Assertions.assertThat(rolGuardado.getNombre()).isNotNull();
        Assertions.assertThat(rolGuardado.getNombre().name()).isEqualTo(rol.getNombre().name());
        Assertions.assertThat(rolGuardado.getCreacion()).isNotNull();
        Assertions.assertThat(rolGuardado.getUltimaActualizacion()).isNotNull();
        Assertions.assertThat(rolGuardado.getCreacion()).isCloseTo(rolGuardado.getUltimaActualizacion(), Assertions.within(1, ChronoUnit.SECONDS));
    }

    @Test
    public void RolRepository_save_arrojaDataIntegrityViolationException(){
        // Se guarda un rol ADMIN
        rolRepository.saveAndFlush(Rol.builder().nombre(RolEnum.ADMIN).build());

        Assertions.assertThatThrownBy(() -> {
            // Se intenta nuevamente guardar un rol ADMIN
            rolRepository.saveAndFlush(rol);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void RolRepository_findByNombre_devuelveElRolPorElNombre(){
        Rol rolGuardado = rolRepository.save(rol);

        Optional<Rol> rolOpcional = rolRepository.findByNombre(rolGuardado.getNombre());

        Assertions.assertThat(rolOpcional).isNotNull();
        Assertions.assertThat(rolOpcional).isNotEmpty();
        Assertions.assertThat(rolOpcional.get().getNombre()).isEqualTo(rolGuardado.getNombre());
    }

    @Test
    public void RolRepository_findByNombre_devuelveEmpty(){
        Optional<Rol> rolOpcional = rolRepository.findByNombre(null);

        Assertions.assertThat(rolOpcional).isNotNull();
        Assertions.assertThat(rolOpcional).isEmpty();
    }

}
