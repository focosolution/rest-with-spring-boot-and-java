package br.com.focosolution.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

// Irá retornar o HttpStatus.BAD_REQUEST que será tratado pelo CustomizedResponseEntityExceptionHandler, pois é a 
// classe que possui "@ControllerAdvice" que intercepta excessões disparadas por classes com "@RequestMapping" 
@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends AuthenticationException{

	private static final long serialVersionUID = 1L;
	
	public InvalidJwtAuthenticationException(String exception) {
		super(exception);
	}
	
}
