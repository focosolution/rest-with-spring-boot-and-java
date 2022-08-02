package br.com.focosolution;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Person findById(@PathVariable(value = "id") Long paramId) {
		return service.findById(paramId);
	}

	// Utilizando Path Params, exemplo: http://localhost:8080/person
	@RequestMapping(method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findAll() {
		return service.findAll();
	}	

	// Deve ser chamado por uma aplicação, como o Postaman. Para isso devemos escolher "Body"+"raw"+"JSON"
	// Vale observar que o método create utiliza a annotation @RequestBody
	@RequestMapping(method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person create(@RequestBody Person person) {
		return service.create(person);
	}	

	// Bastante similar ao POST
	@RequestMapping(method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person update(@RequestBody Person person) {
		return service.update(person);
	}
	
	// No caso do delete é preferível utilizar o Path Params para assim evitar travegar um JSON na rede. 
	// Porém não é possível chamá-lo pelo browser, pois ele seria tratado como o GET (findById)
	// Executando pelo Postman percebemos que foi Okay pois ele retornou o código 200 OK
	@RequestMapping(value = "/{id}", 	
			method=RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") Long paramId) {
		service.delete(paramId);
	}	

}
