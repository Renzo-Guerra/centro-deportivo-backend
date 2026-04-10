package org.learning.sistemacanchas.service;

import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.DTOs.LoguearseDTOReq;
import org.learning.sistemacanchas.DTOs.LoguearseDTORes;
import org.learning.sistemacanchas.DTOs.UsuarioDTOReq;
import org.learning.sistemacanchas.DTOs.UsuarioDTORes;
import org.learning.sistemacanchas.entity.Rol;
import org.learning.sistemacanchas.entity.Usuario;
import org.learning.sistemacanchas.enums.RolEnum;
import org.learning.sistemacanchas.exception.CredencialesInvalidasException;
import org.learning.sistemacanchas.exception.NoEncontradoException;
import org.learning.sistemacanchas.repository.RolRepository;
import org.learning.sistemacanchas.repository.UsuarioRepositorio;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutenticacionServiceImp implements AutenticacionService{
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RolRepository rolRepository;

    @Override
    @Transactional
    public UsuarioDTORes registrarse(UsuarioDTOReq request, RolEnum rolEnum) {
        Rol rol = rolRepository.findByNombre(rolEnum)
                .orElseThrow(() -> new NoEncontradoException("No se logró encontrar el rol '" + rolEnum.name() + "'!"));

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .rol(rol)
                .build();

        Usuario usuarioGuardado = usuarioRepositorio.save(nuevoUsuario);

        return UsuarioDTORes.builder()
                .id(usuarioGuardado.getId())
                .nombre(usuarioGuardado.getNombre())
                .apellido(usuarioGuardado.getApellido())
                .email(usuarioGuardado.getEmail())
                .creacion(usuarioGuardado.getCreacion())
                .ultimaActualizacion(usuarioGuardado.getUltimaActualizacion())
                .rol(usuarioGuardado.getRol())
                .build();
    }

    @Override
    @Transactional
    public LoguearseDTORes login(LoguearseDTOReq request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getContrasenia()
                    )
            );
        }catch (AuthenticationException e){
            throw new CredencialesInvalidasException("Email o contraseña incorrectos");
        }

        Usuario usuario = usuarioRepositorio.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoEncontradoException("No se encontró al usuario con el mail '" + request.getEmail() + "'!"));


        String token = jwtService.generateToken(usuario);

        return LoguearseDTORes.builder()
                .token(token)
                .build();
    }
}
