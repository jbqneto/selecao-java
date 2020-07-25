package io.jbqneto.desafioindra.repositories.empresa;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jbqneto.desafioindra.models.empresa.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
	
	public Historico findById(long id);

}
