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
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
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
                        // Permitir acesso a:
                        // / (home), /Sorvete (home), /Sorvete/informacoes (dashboard de sorvetes),
                        // /Sorvete/precos (preços de sorvetes), /Sorvete/lista-tabela (NOVO: tabela de sorvetes),
                        // /Cliente/informacoes (dashboard de clientes)
                        // registro, login, recursos estáticos, console H2.
                        .requestMatchers("/", "/Sorvete", "/Sorvete/informacoes", "/Sorvete/precos", "/Cliente","/Sorvete/lista-tabela", "/Cliente/informacoes", "/register", "/loga", "/css/**", "/js/**", "/h2-console/**", "/login").permitAll()
                        .anyRequest().authenticated() // Todas as outras requisições (como cadastro e edição) precisam de autenticação
                )
                .formLogin(form -> form
                        .loginPage("/loga")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/Sorvete", true) // Redireciona para /Sorvete (sua nova home) após login
                        .failureUrl("/loga?error=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/loga?logout=true")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().disable());

        System.out.println("DEBUG: SecurityConfig - SecurityFilterChain construído.");
        return http.build();
    }
}
