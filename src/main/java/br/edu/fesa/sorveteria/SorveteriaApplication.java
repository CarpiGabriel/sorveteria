package br.edu.fesa.sorveteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean; // Importe esta classe
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importe esta classe
import org.springframework.security.crypto.password.PasswordEncoder; // Importe esta classe

@SpringBootApplication
public class SorveteriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SorveteriaApplication.class, args);
	}

	// ADICIONE ESTE BEAN AQUI. Ele será carregado muito cedo e estará disponível.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
