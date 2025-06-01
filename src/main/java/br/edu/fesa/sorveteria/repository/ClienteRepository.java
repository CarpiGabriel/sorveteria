package br.edu.fesa.sorveteria.repository;

import br.edu.fesa.sorveteria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importe esta anotação

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método para contar o total de clientes
    long count(); // JpaRepository já fornece count(), mas podemos usar um @Query também se preferir

    // Query para calcular a média das idades dos clientes
    @Query("SELECT AVG(c.idade) FROM Cliente c WHERE c.idade IS NOT NULL")
    Double findAverageIdade();
}
