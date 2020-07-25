package io.jbqneto.desafioindra.repositories.empresa;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jbqneto.desafioindra.models.empresa.Revenda;

public interface RevendaRepository extends JpaRepository<Revenda, Long> {

	public Revenda findById(long id);
	public Revenda findByCnpj(String cnpj);
}
