package br.com.focosolution.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.focosolution.repositories.UserRepository;

/* O anotation abaixo serve para que o Spring encare esse serviço como um objeto que vai ser injetado 
 * em runtime em outras classes da nossa aplicação. Desssa forma eu não preciso instanciar este serviço 
 * quando for utilizá-lo no Controller. Mas para isso eu precisarei utilizar o @Autowired no Controller 
 */ 
@Service
public class UserServices implements UserDetailsService {
	
	private Logger logger = Logger.getLogger(UserServices.class.getName());
	
	/* Aqui foi implementada a injeção de dependência via propriedade / field. Mas também poderia
	 * ser via construtor, para isso bastaria colocar o @Autowired antes do construtor. A desvantagem
	 * em trabalhar desta forma é que podemos ter um NullpointerException, caso essa classe seja chamada 
	 * sem que haja uma instância da mesma.
	 */
	@Autowired
	UserRepository repository;
	
	public UserServices(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Procurando uma pessoa pelo nome " + username + "!");
		var user = repository.findByUserName(username);
		if (user != null) {
			logger.info("Estou em loadUserByUsername... user = "+user.getUsername());
			return user;
		} else {
			throw new UsernameNotFoundException("Usuário " + username + " não encontrado!");
		}
	}

}
