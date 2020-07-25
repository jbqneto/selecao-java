package io.jbqneto.desafioindra.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jbqneto.desafioindra.models.Usuario;
import io.jbqneto.desafioindra.repositories.UsuarioRepository;
import io.jbqneto.desafioindra.security.encoders.BCryptEncoder;
import io.jbqneto.desafioindra.security.encoders.StringEncoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
@Api(value="API de Usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepo;
	
	@GetMapping("/usuario")
	@ApiOperation(value = "Retorna a lista de usuários cadastrados no sistema")
	public List<Usuario> getUsuarios() {
		return usuarioRepo.findAll();
	}
	
	@GetMapping("/usuario/{id}")
	@ApiOperation(value = "Retorna usuário definido no ID")
	public Usuario getUsuario(@PathVariable long id) {
		return usuarioRepo.findById(id);
	}
	
	@DeleteMapping("/usuario/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deleta o usuário definido no ID")
	public void deleteUsuario(@PathVariable long id) {
		
		Usuario usuario = usuarioRepo.findById(id);
		
		if (usuario == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
		
		usuarioRepo.delete(usuario);
	}
	
	@PutMapping("/usuario/{id}")
	@ApiOperation(value = "Edita o usuário com base no ID e CONTEUDO passados na operação.")
	public Usuario editUsuario(@PathVariable(value="id") long id, @RequestBody Usuario usuario) {
		try {
			
			Usuario usuarioPorId = usuarioRepo.findById(id);
			
			if (usuarioPorId == null)
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
			
			usuario.setId(id);
			usuarioRepo.save(usuario);
			
			return usuario;
			
		} catch (ResponseStatusException r) {
			throw r;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Erro ao inesperado ao editar usuário.");	
		}
	}
	
	@PostMapping("/usuario")
	@ResponseStatus(code = HttpStatus.CREATED, value = HttpStatus.CREATED)
	@ApiOperation(value = "Cria um novo usuário")
	public Usuario saveUsuario(@RequestBody Usuario usuario) {
		try {			
			
			StringEncoder encoder = new BCryptEncoder();
			
			boolean isEmailRegistered = (usuarioRepo.findByEmail(usuario.getEmail()) != null);
			
			if (isEmailRegistered)
				throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado.");
			
			boolean isLoginRegistered = (usuarioRepo.findByLogin(usuario.getLogin()) != null);
			
			if (isLoginRegistered)
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um cadastro com esse nome de usuário.");
			
			usuario.setSenha(encoder.encodeString(usuario.getSenha()));
			
			return usuarioRepo.save(usuario);
		
		} catch(ResponseStatusException r) {
			throw r;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Erro ao inesperado ao gravar usuário.");
		}
	}

}
