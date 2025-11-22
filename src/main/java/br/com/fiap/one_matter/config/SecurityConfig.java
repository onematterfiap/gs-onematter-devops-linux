package br.com.fiap.one_matter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // 💡 IMPORTANTE
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true) // 💡 HABILITA O @PreAuthorize NOS CONTROLLERS
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // Rotas públicas de POST (Cadastro, Login, Refresh)
                        .requestMatchers(
                                HttpMethod.POST,
                                "/auth/login",
                                "/auth/register",
                                "/auth/refresh"
                        ).permitAll()

                        // Rotas públicas de GET (Listar/Ver Vagas)
                        .requestMatchers(
                                HttpMethod.GET,
                                "/vagas",
                                "/vagas/{id:[0-9]+}" // Permite GET por ID
                        ).permitAll()

                        // Rotas autenticadas para usuários (Candidatos)
                        .requestMatchers("/usuarios/me").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/me").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/me").authenticated()

                        // Novas rotas de Candidato (USER)
                        .requestMatchers("/candidato/me/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/vagas/{idVaga}/candidatar").hasRole("USER")

                        // Novas rotas de ADMIN (Gerenciamento)
                        .requestMatchers("/admin/users/**").hasRole("ADMIN")
                        .requestMatchers("/vagas/**").hasRole("ADMIN")
                        .requestMatchers("/empresas/**").hasRole("ADMIN")
                        .requestMatchers("/recrutadores/**").hasRole("ADMIN")
                        .requestMatchers("/skills/**").hasRole("ADMIN")
                        .requestMatchers("/questoes/**").hasRole("ADMIN")

                        // A rota /testes agora exige apenas estar logado.
                        .requestMatchers("/testes/**").authenticated()

                        // Rotas públicas de documentação e H2
                        .requestMatchers(
                                "/h2-console/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs/swagger-config"
                        ).permitAll()

                        // Todas as outras rotas exigem autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // Para H2
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}