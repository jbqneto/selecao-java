package io.jbqneto.desafioindra.imports.historico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;

import io.jbqneto.desafioindra.models.empresa.Historico;
import io.jbqneto.desafioindra.models.empresa.Revenda;
import io.jbqneto.desafioindra.models.endereco.Estado;
import io.jbqneto.desafioindra.models.endereco.Municipio;


public class ImportadorHistoricoCsv implements ImportadorHistorico {
	
	private static final String COMMA_DELIMITER = ";";
	static Log log = LogFactory.getLog(ImportadorHistoricoCsv.class.getClass());
	
	/**
	 * Criado para tentar percorrer o arquivo original
	 * @param isr
	 * @return
	 * @throws Exception
	 */
	private List<Historico> readWithOpenCsv(InputStreamReader isr) throws Exception {
		List<Historico> historicos = new ArrayList<Historico>();
		CSVReader reader = null;
		try (BufferedReader br = new BufferedReader(isr)) {
			reader = new CSVReader(br);
			String[] nextLine;
			int countRow = 0;
			int countCol = 0;
            while ((nextLine = reader.readNext()) != null) {
            	System.out.println(countRow + " - ROW: ");
            	
                for (String e : nextLine) {
                    System.out.println(e);
                    countCol++;
                }
                System.out.println("\n");
                
                countRow++;
            }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			reader.close();
		}
		
		return historicos;
	}
	
	private List<Historico> readWithPlainText(InputStreamReader isr) throws Exception {
		int count = 0;
		List<Historico> historicos = new ArrayList<Historico>();
		
		try (BufferedReader br = new BufferedReader(isr)) {
			String line;
			while ((line = br.readLine()) != null) {

				if (count == 0) {
					count++;
					log.info(line);
					continue;
				}

				try {
					
					String[] cols = line.split(COMMA_DELIMITER);
					
					if (cols.length < 10)
						continue;

					Historico historico = readHistoricoFromLine(cols, count);
					historicos.add(historico);
					count++;
				} catch (Exception e) {
					log.fatal("Error on count: " + count, e);
					throw e;
				}
			}
		}	
		
		return historicos;
	}

	@Override
	public List<Historico> importar(MultipartFile arquivo) throws Exception {

		InputStreamReader isr = new InputStreamReader(arquivo.getInputStream());
		
		return readWithPlainText(isr);
	}
	
	
	private Historico readHistoricoFromLine(String[] colunas, int count) throws Exception {
		
		Estado estado = new Estado();
		estado.setRegiao(colunas[0]);
		estado.setUf(colunas[1]);

		Municipio municipio = new Municipio();
		municipio.setEstado(estado);
		municipio.setNome(colunas[2]);

		Revenda revenda = new Revenda();
		revenda.setMunicipio(municipio);
		revenda.setNome(colunas[3]);
		revenda.setBandeira(colunas[10]);
		
		String strCnpj = colunas[4];
		
		revenda.setCnpj(strCnpj);

		Historico historico = new Historico();
		historico.setRevenda(revenda);
		historico.setProduto(colunas[5].trim());

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date dateColeta = format.parse(colunas[6].trim());

		LocalDate dataColeta = dateColeta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		historico.setDataColeta(dataColeta);

		String strValorVenda = colunas[7].trim();
		String strValorCompra = colunas[8].trim();

		Double valVenda = 0.0;
		Double valCompra = 0.0;

		if (strValorVenda != null && strValorVenda.length() > 0)
			valVenda = Double.parseDouble(strValorVenda.replace(",", "."));;

			if (strValorCompra != null && strValorCompra.length() > 0)
				valCompra = Double.parseDouble(strValorCompra.replace(",", "."));

			historico.setValorVenda(BigDecimal.valueOf(valVenda));
			historico.setValorCompra(BigDecimal.valueOf(valCompra));
			historico.setUnidadeMedida(colunas[9]);


			return historico;
	}

}
