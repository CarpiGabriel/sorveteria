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

// Páginas acessíveis a QUALQUER UM (permitAll) - apenas login, registro e recursos estáticos
                                .requestMatchers("/",
                                        "/loga", "/register", "/css/**", "/js/**", "/h2-console/**", "/login", "/error"
                                ).permitAll()

// Páginas que exigem a role ADMIN para operações de alteração
                                .requestMatchers(
                                        "/Sorvete/formulario", // Cadastro/Edição de Sorvete
                                        "/Sorvete/editar/**", // Edição de Sorvete
                                        "/Sorvete/excluir/**", // Exclusão de Sorvete
                                        "/Cliente/novo", // Cadastro de Cliente
                                        "/Cliente/editar/**", // Edição de Cliente
                                        "/Cliente/excluir/**" // Exclusão de Cliente
                                ).hasRole("ADMIN") // Apenas usuários com a role "ADMIN"
// Todas as outras requisições (incluindo /Sorvete, /Cliente, /Sorvete/informacoes, etc.)
// exigem autenticação.
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/loga")
                        .usernameParameter("username")
                        .passwordParameter("password")

                        .defaultSuccessUrl("/Sorvete", true) // Redireciona para a home após login
                        .failureUrl("/loga?error=true")
                )                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/loga?logout=true")

                        .permitAll() // Permitir acesso à URL de logout

                )
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().disable());

        System.out.println("DEBUG: SecurityConfig - SecurityFilterChain construído.");
        return http.build();
    }
}