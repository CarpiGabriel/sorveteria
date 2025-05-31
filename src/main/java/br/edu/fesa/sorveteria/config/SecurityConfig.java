package br.edu.fesa.sorveteria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Removemos os beans DaoAuthenticationProvider e ProviderManager.
    // Vamos configurar o AuthenticationManagerBuilder diretamente.

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        System.out.println("DEBUG: SecurityConfig - AuthenticationManagerBuilder configurado com UserDetailsService e PasswordEncoder.");
        return authenticationManagerBuilder.build();
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
                        .defaultSuccessUrl("/Cliente", true)
                        .failureUrl("/loga?error=true")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().disable());

        System.out.println("DEBUG: SecurityConfig - SecurityFilterChain construído.");
        return http.build();
    }

    // Remova este Bean, pois já está no AppConfig.java
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
}
