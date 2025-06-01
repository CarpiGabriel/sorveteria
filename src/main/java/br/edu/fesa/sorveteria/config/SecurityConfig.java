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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importe esta classe
import org.springframework.security.crypto.password.PasswordEncoder; // Importe esta classe
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    // Injetamos o PasswordEncoder aqui, ele virá do AppConfig.java
    @Autowired
    private PasswordEncoder passwordEncoder;

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
                        .requestMatchers("/", "/index", "/register", "/loga", "/css/**", "/js/**", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/loga")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true) // Redireciona para a raiz (seu Dashboard)
                        .failureUrl("/loga?error=true")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().disable());

        System.out.println("DEBUG: SecurityConfig - SecurityFilterChain construído.");
        return http.build();
    }

    // REMOVA ESTE BEAN DAQUI. Ele deve estar apenas no AppConfig.java para evitar o ciclo.
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
}
