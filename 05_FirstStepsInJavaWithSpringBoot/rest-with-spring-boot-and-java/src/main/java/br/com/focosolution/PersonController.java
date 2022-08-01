package br.com.focosolution;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.focosolution.model.Person;
import br.com.focosolution.services.PersonServices;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	// A annotation abaixo faz a injeção do serviço, sem precisar instanciá-lo.
	@Autowired
	private PersonServices service;
	
	// Utilizando Path Params, exemplo: http://localhost:8080/person/1
	@RequestMapping(value = "/{id}", 	
			method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findById(@PathVariable(value = "id") String paramId) {
		return service.findById(paramId);
	}
	
	@RequestMapping(method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findAll() {
		return service.findAll();
	}	

}
