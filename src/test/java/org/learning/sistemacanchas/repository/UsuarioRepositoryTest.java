package org.learning.sistemacanchas.repository;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.sistemacanchas.entity.Rol;
import org.learning.sistemacanchas.entity.Usuario;
import org.learning.sistemacanchas.enums.RolEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.temporal.ChronoUnit;

@DataJpaTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private RolRepository rolRepository;

    private Usuario usuario;
    private Rol rol;

    @BeforeEach
    public void init(){
        rol = Rol.builder()
                .nombre(RolEnum.ADMIN)
                .build();

        usuario = Usuario.builder()
                .nombre("Miguel")
                .apellido("Angel")
                .email("miguelangel@gmail.com")
                .contrasenia("123456789")
                .rol(rol)
                .build();
    }

    @Test
    public void UsuarioRepository_save_guardaNuevaEntidad(){
        rolRepository.save(rol);
        Usuario usuarioGuardado = usuarioRepositorio.save(usuario);

        Assertions.assertThat(usuarioGuardado).isNotNull();
        Assertions.assertThat(usuarioGuardado.getCreacion()).isNotNull();
        Assertions.assertThat(usuarioGuardado.getCreacion()).isCloseTo(usuarioGuardado.getUltimaActualizacion(), Assertions.within(1, ChronoUnit.SECONDS));
    }

    @Test
    public void UsuarioRepository_save_arrojaDataIntegrityViolationException(){
        rolRepository.save(rol);
        usuarioRepositorio.saveAndFlush(usuario);
        Usuario usuarioMismoMail = Usuario.builder()
                .nombre("Jorge")
                .apellido("Gonzalez")
                .email(usuario.getEmail())
                .contrasenia("password123")
                .rol(rol)
                .build();

        Assertions.assertThatThrownBy(()->{
            usuarioRepositorio.saveAndFlush(usuarioMismoMail);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void UsuarioRepository_save_arrojaConstraintViolationException(){
        rolRepository.save(rol);
        usuario.setEmail("a@s.com");

        Assertions.assertThatThrownBy(()->{
            usuarioRepositorio.save(usuario);
        }).isInstanceOf(ConstraintViolationException.class);
    }
}
