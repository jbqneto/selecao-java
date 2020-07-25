package io.jbqneto.desafioindra.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import io.jbqneto.desafioindra.imports.historico.ImportadorHistorico;
import io.jbqneto.desafioindra.imports.historico.ImportadorHistoricoCsv;
import io.jbqneto.desafioindra.models.empresa.Historico;
import io.jbqneto.desafioindra.models.empresa.Revenda;
import io.jbqneto.desafioindra.repositories.empresa.HistoricoRepository;
import io.jbqneto.desafioindra.repositories.empresa.RevendaRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class HistoricoController {
	
	static Log log = LogFactory.getLog(HistoricoController.class.getClass());

	@Autowired
	HistoricoRepository historicoRepo;
	
	@Autowired
	RevendaRepository revendaRepo;
	
	@PostMapping("/historico/importar")
	public void importarArquivo(@RequestParam MultipartFile arquivo) {
		log.info(arquivo.getContentType());
		log.info(arquivo.getName());
		log.info(arquivo.getOriginalFilename());
		
		try {
			ImportadorHistorico importadorCsv = new ImportadorHistoricoCsv();
			List<Historico> historicos = importadorCsv.importar(arquivo);
			log.info("arquivos: " + historicos.size());
			
			//UF|ID
			Map<String, Long> mapEstadoPorUfId = new HashMap<String, Long>();
			
			//CNPJ|ID
			Map<String, Long> mapRevendaCnpjId = new HashMap<String, Long>();
					
			for (Historico historico: historicos) {
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
			log.debug("Erro ao importar o CSV", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno. Tente novamente mais tarde.");
		}
		
		
	}
	
	@GetMapping("/historico")
	public List<Historico> getHistoricos() {
		return this.historicoRepo.findAll();
	}
	
	@GetMapping("/historico/{id}")
	public Historico getHistorico(@PathVariable("id") long id) {
		
		Historico historico = this.historicoRepo.findById(id);
		
		if (historico == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Historico não encontrado: " + id);
	
		return historico;
	}
	
	@DeleteMapping("/historico/{id}")
	public void deleteHistorico(@PathVariable("id") long id) {
		Historico historico = this.getHistorico(id);
	
		this.historicoRepo.delete(historico);
	}
	
	@PostMapping("/historico")
	public Historico createHistorico(@RequestBody Historico historico) {
		try {
			Revenda revenda = revendaRepo.findById(historico.getRevenda().getId());		

			if (revenda == null)
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Revenda não encontrada: " + historico.getRevenda().getId());
			
			historico.setRevenda(revenda);
			
			return historicoRepo.save(historico);
		} catch (ResponseStatusException r) {
			throw r;
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no servidor. Tente novamente mais tarde.");
		}
		
	}
	
	@PutMapping("/historico/{id}")
	public Historico updateHistorico(@PathVariable("id") long id,  @RequestBody Historico historico) {
		
		Historico historiById = getHistorico(id);
		
		if (historico.getId() > 0 && (historico.getId() != historiById.getId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Parametros 'ID' de rota e do objeto diferem.");
		
		historico.setId(id);
		
		return this.save(historico);
	}
	
	/**
	 * Aplicando as validaçoes que coincidem entre CREATE e UPDATE
	 * @param historico
	 * @return
	 */
	private Historico save(Historico historico) {
		Revenda revenda = revendaRepo.findById(historico.getRevenda().getId());
		
		if (revenda == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Revenda não encontrada: " + historico.getRevenda().getId());
		
		return historicoRepo.save(historico);
	}
	
}
