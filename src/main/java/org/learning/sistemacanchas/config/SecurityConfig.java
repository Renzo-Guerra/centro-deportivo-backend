package org.learning.sistemacanchas.config;


import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.enums.RolEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                // Definir endpoints publicos o privados
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicEndpoints()).permitAll()
                        .requestMatchers(adminEndpoints()).hasAuthority(RolEnum.ADMIN.name())
                        .anyRequest().authenticated()) // Any other needs to be authenticated
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    private RequestMatcher publicEndpoints(){
        PathPatternRequestMatcher.Builder builder = PathPatternRequestMatcher.withDefaults();

        return new OrRequestMatcher(List.of(
                builder.matcher(HttpMethod.POST, "/api/autenticacion/login")
        ));
    }

    private RequestMatcher adminEndpoints(){
        PathPatternRequestMatcher.Builder builder = PathPatternRequestMatcher.withDefaults();

        return new OrRequestMatcher(List.of(
                builder.matcher(HttpMethod.GET, "/api/canchas/**"),
                builder.matcher(HttpMethod.POST, "/api/canchas/**"),
                builder.matcher(HttpMethod.POST, "/api/turnos/**")
        ));
    }
}
