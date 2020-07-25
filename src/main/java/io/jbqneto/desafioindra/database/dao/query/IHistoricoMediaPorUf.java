package io.jbqneto.desafioindra.database.dao.query;

import java.math.BigDecimal;

public interface IHistoricoMediaPorUf {
	Long getUfId();
	String getUf();
	BigDecimal getCompra();
	BigDecimal getVenda();

}
