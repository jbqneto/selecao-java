package io.jbqneto.desafioindra.database.dao.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import io.jbqneto.desafioindra.database.dao.AbstractSaveRepository;
import io.jbqneto.desafioindra.models.empresa.Revenda;
import io.jbqneto.desafioindra.repositories.empresa.RevendaRepository;

public class RevendaDao extends AbstractSaveRepository<Revenda> {

	public RevendaDao(JpaRepository<Revenda, Long> repository) {
		super(repository);
	}
	
	@Override
	public Revenda save(Revenda revenda) {
		
		Revenda revendaPorCnpj = this.getRepository().findByCnpj(revenda.getCnpj());
		
		if (revendaPorCnpj != null && revendaPorCnpj.getId() != revenda.getId())
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Já existe outra empresa cadastrada com esse cnpj.");
		
		return this.repository.save(revenda);
	}

	@Override
	public RevendaRepository getRepository() {
		return (RevendaRepository) this.repository;
	}
	
}
