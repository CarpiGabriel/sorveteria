package br.edu.fesa.sorveteria.repository;

import br.edu.fesa.sorveteria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsernameAndSenha(String username, String senha);
}
