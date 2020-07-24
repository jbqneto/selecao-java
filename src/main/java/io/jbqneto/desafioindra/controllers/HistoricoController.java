package io.jbqneto.desafioindra.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class HistoricoController {

	@PostMapping("/historico/importar")
	public void importarArquivo(@RequestParam("aarquivo") MultipartFile arquivo) {
		
	}
	
}
