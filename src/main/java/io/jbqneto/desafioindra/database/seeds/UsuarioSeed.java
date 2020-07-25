package io.jbqneto.desafioindra.database.seeds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import io.jbqneto.desafioindra.models.Usuario;
import io.jbqneto.desafioindra.repositories.UsuarioRepository;

public class UsuarioSeed {

	@Autowired
	UsuarioRepository usuarioRepo;
	
	@EventListener
	public void addAdminUser(ContextRefreshedEvent event) {
		Usuario usuario = new Usuario();
		usuario.setNome("José Bezerra de Queiroz Neto");
		usuario.setEmail("jbqneto@gmail.com");
		usuario.setSenha("123456");
		
		usuarioRepo.save(usuario);
	}
	
}
