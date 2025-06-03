package br.edu.fesa.sorveteria.config;

import org.springframework.context.annotation.Configuration;
// Remova estas importações se não houver mais nenhum @Bean aqui
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    // REMOVA ESTE BLOCO INTEIRO. O PasswordEncoder agora está em SorveteriaApplication.java
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
}
