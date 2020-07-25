package io.jbqneto.desafioindra.database.dao.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import io.jbqneto.desafioindra.database.dao.AbstractSaveRepository;
import io.jbqneto.desafioindra.models.endereco.Estado;
import io.jbqneto.desafioindra.repositories.endereco.EstadoRepository;

public class EstadoDao extends AbstractSaveRepository<Estado> {
	
	public EstadoDao(JpaRepository<Estado, Long> repository) {
		super(repository);
	}

	public EstadoRepository getRepository() {
		return (EstadoRepository) this.repository;
	}
	
	@Override
	public Estado save(Estado estado) {
		if (estado.getId() == 0) {
			
			Estado estadoPorUf = this.getRepository().findByUf(estado.getUf());
			
			if (estadoPorUf != null) {
				return estadoPorUf;
			} else {
				return getRepository().save(estado);			
			}
		} else {
			Estado estadoPorId = getRepository().findById(estado.getId());
			
			if (estadoPorId == null)
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado não encontrado. Id: " + estado.getId());
			
			return estadoPorId;
		}
	}

	@Override
	public Estado findById(long id) {
		return this.getRepository().findById(id);
	}

}
