package br.edu.fesa.sorveteria.repository;

import br.edu.fesa.sorveteria.model.Sorvete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importe esta anotação

public interface SorveteRepository extends JpaRepository<Sorvete, Long> {

    // Query para calcular a média dos preços dos sorvetes
    @Query("SELECT AVG(s.preco) FROM Sorvete s")
    Double findAveragePreco();

}
