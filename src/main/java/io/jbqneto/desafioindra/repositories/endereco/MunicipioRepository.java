package io.jbqneto.desafioindra.repositories.endereco;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jbqneto.desafioindra.models.endereco.Municipio;

public interface MunicipioRepository extends JpaRepository<Municipio, Long>  {

	public Municipio findById(long id);
	public Municipio findByEstadoIdAndNome(long estadoId, String nome);
}
