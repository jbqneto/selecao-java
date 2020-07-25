package io.jbqneto.desafioindra.models.empresa;

import java.io.Serializable;
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
public class Historico implements Serializable {

	private static final long serialVersionUID = 1L;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataColeta() {
		return dataColeta;
	}

	public void setDataColeta(Date dataColeta) {
		this.dataColeta = dataColeta;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Revenda getRevenda() {
		return revenda;
	}

	public void setRevenda(Revenda revenda) {
		this.revenda = revenda;
	}
	
}
