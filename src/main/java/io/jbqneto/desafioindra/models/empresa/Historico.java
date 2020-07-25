package io.jbqneto.desafioindra.models.empresa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Historico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "DATA_COLETA", nullable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataColeta;
	
	@Column(name = "VALOR_VENDA")
	@Digits(integer = 4, fraction = 3)
	@DecimalMin(value = "0.1", message = "Informe o valor de venda.")
	private BigDecimal valorVenda;
	
	@Column(name = "VALOR_COMPRA")
	private BigDecimal valorCompra;
	
	@Column(name = "UNIDADE_MEDIDA")
	private String unidadeMedida;
	
	@Column(name = "PRODUTO")
	private String produto;
	
	@ManyToOne
	@JoinColumn(name = "REVENDA_ID")
	private Revenda revenda;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDataColeta() {
		return dataColeta;
	}

	public void setDataColeta(LocalDate dataColeta) {
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
