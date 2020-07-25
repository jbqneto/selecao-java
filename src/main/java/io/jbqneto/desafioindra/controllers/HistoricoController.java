package io.jbqneto.desafioindra.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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

import io.jbqneto.desafioindra.database.dao.query.IHistoricoMediaPorUf;
import io.jbqneto.desafioindra.imports.historico.ImportadorHistorico;
import io.jbqneto.desafioindra.imports.historico.ImportadorHistoricoCsv;
import io.jbqneto.desafioindra.models.empresa.Historico;
import io.jbqneto.desafioindra.models.empresa.Revenda;
import io.jbqneto.desafioindra.models.endereco.Estado;
import io.jbqneto.desafioindra.models.endereco.Municipio;
import io.jbqneto.desafioindra.repositories.empresa.HistoricoRepository;
import io.jbqneto.desafioindra.repositories.empresa.RevendaRepository;
import io.jbqneto.desafioindra.repositories.endereco.EstadoRepository;
import io.jbqneto.desafioindra.repositories.endereco.MunicipioRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
@Api(value = "API de Histórico de compra/venda de combustível")
public class HistoricoController {
	
	static Log log = LogFactory.getLog(HistoricoController.class.getClass());

	@Autowired
	HistoricoRepository historicoRepo;
	
	@Autowired
	RevendaRepository revendaRepo;
	
	@Autowired
	EstadoRepository estadoRepo;
	
	@Autowired
	MunicipioRepository municipioRepo;
	
	@PostMapping("/historico/importar")
	@Transactional(timeout = 600)
	@ApiOperation(value = "Importa os históricos de venda por UPLOAD de arquivo CSV.")
	public List<Historico> importarArquivo(@RequestParam MultipartFile arquivo) {
		log.info(arquivo.getContentType());
		log.info(arquivo.getName());
		log.info(arquivo.getOriginalFilename());
		
		try {
			ImportadorHistorico importadorCsv = new ImportadorHistoricoCsv();
			List<Historico> historicos = importadorCsv.importar(arquivo);
			log.info("arquivos: " + historicos.size());
			
			//Maps para evitar insercao duplicada
			//UF|ID
			Map<String, Long> mapEstadoPorUfId = new HashMap<String, Long>();
			
			//CNPJ|ID
			Map<String, Long> mapRevendaCnpjId = new HashMap<String, Long>();
			
			List<Historico> historicosSalvos = new ArrayList<Historico>();
			
			for (Historico historico: historicos) {
				
				try {
					Revenda revenda = historico.getRevenda();
					Municipio municipio = revenda.getMunicipio();
					Estado estado = municipio.getEstado();
					
					String uf = estado.getUf();
					String cnpjRevenda = revenda.getCnpj();
					
					if (mapRevendaCnpjId.containsKey(cnpjRevenda)) {
						revenda.setId(mapRevendaCnpjId.get(cnpjRevenda));
					} else {
						Revenda revendaPorCnpj = revendaRepo.findByCnpj(cnpjRevenda);
						
						if (revendaPorCnpj != null) {
							revenda.setId(revendaPorCnpj.getId());
						}
						
					}
					
					if (revenda.getId() > 0) {
						historico.setRevenda(revenda);
					} else {
						if (mapEstadoPorUfId.containsKey(estado.getUf())) {
							estado.setId(mapEstadoPorUfId.get(uf));
						} else {
							Estado estadoPorUf = estadoRepo.findByUf(uf);
							
							if (estadoPorUf == null) {
								estado = estadoRepo.save(estado);
							} else {
								estado.setId(estadoPorUf.getId());
							}
							
							mapEstadoPorUfId.put(uf, estado.getId());
							
						}
						
						Municipio municipioExistente = municipioRepo.findByEstadoIdAndNome(estado.getId(), municipio.getNome());
						
						if (municipioExistente != null) {
							municipio.setId(municipioExistente.getId());
						} else {
							municipio = municipioRepo.save(municipio);
						}
						
						Municipio municipioRevenda = new Municipio();
						municipioRevenda.setId(municipio.getId());

						revenda.setMunicipio(municipioRevenda);
						
						revendaRepo.save(revenda);
					}
					
					historico.setRevenda(revenda);
					mapRevendaCnpjId.put(cnpjRevenda, revenda.getId());
					
					historico = createHistorico(historico);			
					
					historicosSalvos.add(historico);
					
				} catch (Exception e) {
					log.info("Erro ao importar historico", e);
				}
				
			}
			
			log.info("salvos: " + historicosSalvos.size());
			
			return historicosSalvos;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
			log.debug("Erro ao importar o CSV", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno. Tente novamente mais tarde.");
		}
		
		
	}
	
	@GetMapping("/historico")
	@ApiOperation(value = "Retorna lista de Históricos cadastrados no sistema")
	public List<Historico> getHistoricos() {
		return this.historicoRepo.findAll();
	}
	
	@GetMapping("/historico/uf/{uf}")
	@ApiOperation(value = "Retorna lista de históricos pela UF")
	public List<Historico> getHistoricoPorUf(@PathVariable String uf) {
		
		return historicoRepo.findByRevendaMunicipioEstadoUf(uf);
	}
	
	/**
	 * TODO Corrigir (nao deu tempo)
	@GetMapping("/historico/preco/{uf}")
	@ApiOperation(value = "Retorna média de preços por UF")
	public List<IHistoricoMediaPorUf> getMediaPrecosPorUf(@PathVariable String uf) {
		
		return historicoRepo.findMediaPrecoCombustiveisPorUf(uf);
	} */
	
	@GetMapping("/historico/regiao/{regiao}")
	@ApiOperation(value = "Retorna lista de históricos pela UF")
	public List<Historico> getHistoricoPorRegiao(@PathVariable String regiao) {
		
		return historicoRepo.findByRevendaMunicipioEstadoUf(regiao);
	}
	
	@GetMapping("/historico/municipio/{municipio}")
	@ApiOperation(value = "Retorna lista de históricos pela UF")
	public List<Historico> getHistoricoPorNomeMunicipio(@PathVariable String municipio) {
		
		return historicoRepo.findByRevendaMunicipioNome(municipio);
	}
	
	@GetMapping("/historico/{id}")
	@ApiOperation(value = "Retorna um determiando histórico pelo ID")
	public Historico getHistorico(@PathVariable("id") long id) {
		
		Historico historico = this.historicoRepo.findById(id);
		
		if (historico == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Historico não encontrado: " + id);
	
		return historico;
	}
	
	@DeleteMapping("/historico/{id}")
	@ApiOperation(value = "Deleta um histórico pelo ID passado.")
	public void deleteHistorico(@PathVariable("id") long id) {
		Historico historico = this.getHistorico(id);
	
		this.historicoRepo.delete(historico);
	}
	
	@PostMapping("/historico")
	@ApiOperation(value = "Cria um novo histórico")
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
	@ApiOperation(value = "Atualiza os dados de uma determinado Histórico com base no ID e CORPO da requisição.")
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
