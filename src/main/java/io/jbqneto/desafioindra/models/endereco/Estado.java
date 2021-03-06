package io.jbqneto.desafioindra.models.endereco;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "UF", length = 2, unique = true)
	@NotBlank(message = "Necessário preencher a UF do estado.")
	private String uf;
	
	@Column(name = "REGIAO")
	private String regiao;
	
	@OneToMany(mappedBy = "estado", cascade = {CascadeType.ALL})
	private List<Municipio> municipios;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
