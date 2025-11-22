package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.Teste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TesteRepository extends JpaRepository<Teste, Long> {
}