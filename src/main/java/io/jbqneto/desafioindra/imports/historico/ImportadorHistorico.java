package io.jbqneto.desafioindra.imports.historico;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.jbqneto.desafioindra.models.empresa.Historico;

public interface ImportadorHistorico {

	public List<Historico> importar(MultipartFile arquivo) throws Exception;
}
