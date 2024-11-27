package com.appkero.backend_kero.infra;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/api/auth/login",
            "/api/auth/refresh",
            "/api/recovery/recover-password",
            "/api/recovery/reset-password",
            "/api/usuario/new",
            "/api/produtos",
            "/api/produtos/{nomeProduto}",
            "/api/produtos/tag/{nomeTag}"
    };

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/api/usuario/foto-perfil",
            "/api/auth/logout",
            "/api/produtos/new/{usuarioId}",
            "/api/produtos/remover/{produtoId}",
            "/api/produtos/atulizar-produto/{produtoId}",
            "/api/usuario/completar-cadastro/{usuarioId}"
    };

    public static final String[] ENDPOINTS_USER = {
            "/api/usuario/test/customer"
    };

    public static final String[] ENDPOINTS_ADMIN = {
            "/api/usuario/all",
            "/api/usuario/{usuarioId}"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable()) // Desativa a proteção contra CSRF
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                    .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")
                    .requestMatchers(ENDPOINTS_USER).hasRole("USER")
                    .anyRequest().authenticated()
                ).addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
