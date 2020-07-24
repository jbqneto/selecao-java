package io.jbqneto.desafioindra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jbqneto.desafioindra.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	public Usuario findById(long id);
	public Usuario findByEmail(String email);
	public Usuario findByLogin(String login);

}
