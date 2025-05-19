package br.edu.fesa.sorveteria.repository;

import br.edu.fesa.sorveteria.model.Sorvete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SorveteRepository extends JpaRepository<Sorvete, Long> {
}
