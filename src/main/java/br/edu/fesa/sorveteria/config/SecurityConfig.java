package br.edu.fesa.sorveteria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Agora injetado da SorveteriaApplication

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder); // Usa o PasswordEncoder injetado
        System.out.println("DEBUG: SecurityConfig - DaoAuthenticationProvider bean criado e configurado.");
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        System.out.println("DEBUG: SecurityConfig - Criando AuthenticationManager com ProviderManager.");
        return new ProviderManager(Collections.singletonList(daoAuthenticationProvider()));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Permitir acesso a todas as páginas públicas, incluindo /login e /Sorvete/informacoes
                        .requestMatchers("/", "/index", "/register", "/loga", "/css/**", "/js/**", "/h2-console/**", "/login", "/Sorvete/informacoes").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/loga") // Sua página de login personalizada
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true) // Redireciona para a raiz (Dashboard) após login
                        .failureUrl("/loga?error=true") // Redireciona para sua página de login com erro
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para processar o logout
                        .logoutSuccessUrl("/loga?logout=true") // Redireciona após logout bem-sucedido
                        .permitAll() // Permitir acesso à URL de logout
                )
                .csrf(csrf -> csrf.disable()) // Desabilitar CSRF para simplificar, mas cuidado em produção
                .headers(headers -> headers.frameOptions().disable()); // Para console H2

        System.out.println("DEBUG: SecurityConfig - SecurityFilterChain construído.");
        return http.build();
    }
}
