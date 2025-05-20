package br.edu.fesa.sorveteria.repository;

import br.edu.fesa.sorveteria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
