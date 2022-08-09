package br.com.focosolution.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.focosolution.data.vo.v1.security.AccountCredentialsVO;
import br.com.focosolution.data.vo.v1.security.TokenVO;
import br.com.focosolution.repositories.UserRepository;
import br.com.focosolution.security.jwt.JwtTokenProvider;

@Service
public class AuthServices {
	
	private Logger logger = Logger.getLogger(AuthServices.class.getName());

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsVO data) {
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			logger.info("username = "+username+" password = "+password);
			
			/*  Ao chamar o "authenticate" abaixo, o spring security executa os seguintes passos: 
			 *  1. Chama o loadUserByUsername do UserServices
			 *  2. Faz a validação do usuário e senha e caso não tenha sucesso, gera um AuthenticationException
			 */
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
			
			logger.info("Após a chamada do authenticationManager.authenticate");
			
			var user = repository.findByUserName(username);
			
			var tokenResponse = new TokenVO();
			if (user != null) {
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			return ResponseEntity.ok(tokenResponse);
		} catch (AuthenticationException e) {
			logger.info("Entrou no AuthenticationException");
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
		var user = repository.findByUserName(username);
		
		var tokenResponse = new TokenVO();
		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		return ResponseEntity.ok(tokenResponse);
	}
}
