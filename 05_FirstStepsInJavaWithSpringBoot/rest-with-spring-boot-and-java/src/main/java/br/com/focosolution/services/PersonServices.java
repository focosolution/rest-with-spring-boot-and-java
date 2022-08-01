package br.com.focosolution.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.focosolution.model.Person;

/* O anotation abaixo serve para que o Spring encare esse serviço como um objeto que vai ser injetado 
   em runtime em outras classes da nossa aplicação. Desssa forma eu não preciso instanciar este serviço 
   quando for utilizá-lo no Controller. Mas para isso eu precisarei utilizar o @Autowired no Controller */ 
@Service
public class PersonServices {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	public List<Person> findAll() {
		
		logger.info("Estou no findAll");
		
		List<Person> persons = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			Person person = mockPerson(i); 
			persons.add(person);
		}
		return persons;
	}
	
	
	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("FirstName "+i);
		person.setLastName("LastName "+i);
		person.setAddress("Address "+i);
		person.setGender("Male");
		return person;
	}


	public Person findById(String id) {
		
		logger.info("Procurando uma pessoa, id = "+id);
		
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Paulo");
		person.setLastName("Freire");
		person.setAddress("Interlagos - São Paulo - SP");
		person.setGender("Male");
		return person;
	}

}
