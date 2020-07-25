package io.jbqneto.desafioindra.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jbqneto.desafioindra.database.dao.empresa.RevendaDao;
import io.jbqneto.desafioindra.models.empresa.Revenda;
import io.jbqneto.desafioindra.models.endereco.Municipio;
import io.jbqneto.desafioindra.repositories.empresa.RevendaRepository;
import io.jbqneto.desafioindra.repositories.endereco.MunicipioRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class RevendaController {

	@Autowired
	private RevendaRepository revendaRepo;
	
	@Autowired
	private MunicipioRepository municipioRepo;
	
	@GetMapping("/revenda")
	public List<Revenda> getRevendas() {
		return revendaRepo.findAll();
	}
	
	@GetMapping("/revenda/{id}")
	public Revenda getRevenda(@PathVariable long id) {
		
		Revenda revenda = revendaRepo.findById(id);
		
		if (revenda == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Revenda não encontrada: " + id);
		
		return revenda;
	}
	
	@DeleteMapping("/revenda/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteRevenda(@PathVariable long id) {
		
		Revenda revenda = revendaRepo.findById(id);
		
		if (revenda == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Revenda não encontrada: " + id);
		
	}
	
	@PostMapping("/revenda")
	@ResponseStatus(code = HttpStatus.CREATED, value = HttpStatus.CREATED)
	public Revenda saveRevenda(@RequestBody Revenda revenda) {
		
		long id = revenda.getMunicipio().getId();
		
		if (id <= 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Necessário informar o ID do município");
		
		Municipio municipio = municipioRepo.findById(id);
		
		if (municipio == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Município não encontrado: " + id);
		
		RevendaDao dao = new RevendaDao(revendaRepo);
		
		Revenda revendaSalva = dao.save(revenda);
		
		return revendaSalva;
	}
	
}
