package io.jbqneto.desafioindra.security.encoders;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Criptografa texto usando o BCryptPasswordEncoder
 * @author Neto
 *
 */
public class BCryptEncoder implements StringEncoder {
	
	BCryptPasswordEncoder bcrypt;
	
	public BCryptEncoder() {
		this.bcrypt = new BCryptPasswordEncoder();
	}
	
	@Override
	public String encodeString(String text) {
		
		return this.bcrypt.encode(text);
	}
}
