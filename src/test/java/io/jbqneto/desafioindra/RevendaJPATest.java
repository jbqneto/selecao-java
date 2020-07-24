package io.jbqneto.desafioindra;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.jbqneto.desafioindra.models.empresa.Revenda;
import io.jbqneto.desafioindra.models.endereco.Estado;
import io.jbqneto.desafioindra.models.endereco.Municipio;
import io.jbqneto.desafioindra.repositories.empresa.RevendaRepository;
import io.jbqneto.desafioindra.repositories.endereco.EstadoRepository;
import io.jbqneto.desafioindra.repositories.endereco.MunicipioRepository;

@DataJpaTest
public class RevendaJPATest {
	
	static Log log = LogFactory.getLog(RevendaJPATest.class.getClass());
	
	@Autowired
	private RevendaRepository repo;
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	@Autowired
	private MunicipioRepository municipioRepo;
	
	@Test
	public void testCreateRevenda() {
		Estado estado = new Estado();
		estado.setRegiao("CO");
		estado.setUf("DF");
		
		estadoRepo.save(estado);
		
		Municipio municipio = new Municipio();
		municipio.setEstado(estado);
		municipio.setNome("BRASILIA");
		
		municipioRepo.save(municipio);
		
		Revenda revenda = new Revenda();
		revenda.setMunicipio(municipio);
		revenda.setBandeira("RAIZEN");
		revenda.setCnpj("10668863000195");
		revenda.setNome("ABRITTA POSTOS DE SERVIÇOS LTDA");
		
		Revenda savedRevenda = repo.save(revenda);
		
		log.info("Revenda salva: " + savedRevenda.getId());
		
		assertNotNull(savedRevenda);
		assertNotNull(savedRevenda.getMunicipio());
		assertNotNull(savedRevenda.getMunicipio().getEstado());
		
		log.info("Fim do test de gravar Revenda");
	}

}
