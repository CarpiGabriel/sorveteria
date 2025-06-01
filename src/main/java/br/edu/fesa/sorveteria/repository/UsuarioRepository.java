package br.edu.fesa.sorveteria.repository;

import br.edu.fesa.sorveteria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    Usuario findByEmail(String email); // <--- Adicione esta linha
}
