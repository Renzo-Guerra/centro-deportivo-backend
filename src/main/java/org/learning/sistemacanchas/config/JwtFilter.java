package org.learning.sistemacanchas.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.learning.sistemacanchas.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {


            final String authHeader = request.getHeader("Authorization");

            // Invalid Authorization or already authenticated
            if (authHeader == null || !authHeader.startsWith("Bearer ") || SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.getUsername(jwt);

            if (userEmail == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // Fail first
            if (!jwtService.isValidToken(jwt, userDetails)) {
                filterChain.doFilter(request, response);
                return;
            }

            // Generate token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // Add token to the context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException e) {
            enviarRespuestaError(response, HttpStatus.UNAUTHORIZED, "El token ha expirado. Por favor, inicie sesión nuevamente.");
        } catch (Exception e) {
            filterChain.doFilter(request, response);
        }
    }

    private void enviarRespuestaError(HttpServletResponse response, HttpStatus status, String mensaje) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"error\": \"" + mensaje + "\" }");
    }
}