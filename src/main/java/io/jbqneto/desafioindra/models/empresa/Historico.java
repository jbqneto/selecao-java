package io.jbqneto.desafioindra.models.empresa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Historico {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "DATA_COLETA", nullable = false)
	@NotEmpty(message = "Preencha a data da coleta")
	private Date dataColeta;
	
	@Column(name = "VALOR_VENDA", nullable = false)
	@NotEmpty(message = "Preencha o valor de venda")
	private BigDecimal valorVenda;
	
	@Column(name = "VALOR_COMPRA")
	private BigDecimal valorCompra;
	
	@Column(name = "UNIDADE_MEDIDA")
	private String unidadeMedida;
	
	@Column(name = "PRODUTO")
	private String produto;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "REVENDA_ID")
	private Revenda revenda;
}
