package io.jbqneto.desafioindra.database.dao.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import io.jbqneto.desafioindra.database.dao.AbstractSaveRepository;
import io.jbqneto.desafioindra.models.endereco.Municipio;
import io.jbqneto.desafioindra.repositories.endereco.MunicipioRepository;

public class MunicipioDao extends AbstractSaveRepository<Municipio> {

	public MunicipioDao(JpaRepository<Municipio, Long> repository) {
		super(repository);
	}
	
	@Override
	public Municipio findById(long id) {
		return this.getRepository().findById(id);
	}

	@Override
	public Municipio save(Municipio entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MunicipioRepository getRepository() {
		return (MunicipioRepository) this.repository;
	}

	

}
