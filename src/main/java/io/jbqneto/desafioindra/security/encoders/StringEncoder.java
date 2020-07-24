package io.jbqneto.desafioindra.security.encoders;

/**
 * 
 * Permite ao usuario criptografar texto da forma como melhor lhe convier.
 * 
 * @author Neto
 * 
 *
 */
public interface StringEncoder {
	
	public String encodeString(String text);
}
