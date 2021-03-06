package io.jbqneto.desafioindra.models.empresa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import io.jbqneto.desafioindra.models.endereco.Municipio;

@Entity
public class Revenda implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "NOME", nullable = false)
	@NotEmpty(message = "Preencha o nome da revenda")
	private String nome;
	
	@Column(name = "CNPJ", nullable = false, length = 14)
	@NotEmpty(message = "Preencha o CNPJ da revenda")
	private String cnpj;
	
	@Column(name = "BANDEIRA")
	private String bandeira;

	@OneToOne
	@JoinColumn(name = "MUNICIPIO_ID", nullable = false)
	private Municipio municipio;

	@OneToMany(mappedBy = "revenda", cascade = {CascadeType.ALL})
	private List<Historico> historicos;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
