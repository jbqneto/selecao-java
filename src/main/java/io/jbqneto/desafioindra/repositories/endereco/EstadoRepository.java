package io.jbqneto.desafioindra.repositories.endereco;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jbqneto.desafioindra.models.endereco.Estado;

public interface EstadoRepository extends  JpaRepository<Estado, Long>  {
	
	public Estado findById(long id);
	public Estado findByUf(String uf);

}
