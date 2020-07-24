package io.jbqneto.desafioindra.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "NOME", nullable = false)
	@NotBlank(message = "Necessárion informar o campo 'nome'.")
	private String nome;
	
	@Column(name = "EMAIL", unique = true, nullable = false)
	@NotBlank(message = "Necessárion informar o campo 'e-mail'.")
	private String email;
	
	@Column(name = "LOGIN", unique = true, nullable = false)
	@NotBlank(message = "Necessárion informar o campo 'login'.")
	private String login;
	
	private String sobre;
	
	@Column(name = "SENHA", length = 100, nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "Necessárion informar o campo 'senha'.")
	private String senha;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String usuario) {
		this.login = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSobre() {
		return sobre;
	}

	public void setSobre(String sobre) {
		this.sobre = sobre;
	}
	
}
