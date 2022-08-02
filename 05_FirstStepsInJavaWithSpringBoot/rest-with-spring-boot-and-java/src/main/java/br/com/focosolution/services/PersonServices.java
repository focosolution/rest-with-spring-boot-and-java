package br.com.focosolution.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.focosolution.exceptions.ResourceNotFoundException;
import br.com.focosolution.model.Person;
import br.com.focosolution.repositories.PersonRepository;

/* O anotation abaixo serve para que o Spring encare esse serviço como um objeto que vai ser injetado 
   em runtime em outras classes da nossa aplicação. Desssa forma eu não preciso instanciar este serviço 
   quando for utilizá-lo no Controller. Mas para isso eu precisarei utilizar o @Autowired no Controller */ 
@Service
public class PersonServices {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;

	public Person findById(Long id) {
		
		logger.info("Procurando uma pessoa, id = "+id);
		
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Paulo");
		person.setLastName("Freire");
		person.setAddress("Interlagos - São Paulo - SP");
		person.setGender("Male");
//		return person;
		// Adicionando um lâmbida funcion para tratar excessão
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado!"));
	}
	
	public Person create(Person person) {
		logger.info("Estou no create!");
		person.setId(counter.incrementAndGet());
//		return person;
		return repository.save(person);
	}

	public Person update(Person person) {
		logger.info("Estou no update!");
//		return person;
		
		var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return repository.save(person);
	}
	
	public void delete(Long id) {
		logger.info("Estou no delete!");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado!"));
		
		repository.delete(entity);
		
 	}	
	
	public List<Person> findAll() {
		
		logger.info("Estou no findAll");
		
//		List<Person> persons = new ArrayList<>();
//		for (int i = 0; i < 8; i++) {
//			Person person = mockPerson(i); 
//			persons.add(person);
//		}
//		return persons;
		return repository.findAll();
	}
	

}
