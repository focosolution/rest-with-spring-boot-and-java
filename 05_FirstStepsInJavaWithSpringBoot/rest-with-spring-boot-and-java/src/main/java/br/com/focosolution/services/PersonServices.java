package br.com.focosolution.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.focosolution.data.vo.v1.PersonVO;
import br.com.focosolution.data.vo.v2.PersonVOV2;
import br.com.focosolution.exceptions.ResourceNotFoundException;
import br.com.focosolution.mapper.DozerMapper;
import br.com.focosolution.mapper.custom.PersonMapper;
import br.com.focosolution.model.Person;
import br.com.focosolution.repositories.PersonRepository;

/* O anotation abaixo serve para que o Spring encare esse serviço como um objeto que vai ser injetado 
   em runtime em outras classes da nossa aplicação. Desssa forma eu não preciso instanciar este serviço 
   quando for utilizá-lo no Controller. Mas para isso eu precisarei utilizar o @Autowired no Controller */ 
@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;	

	public PersonVO findById(Long id) {
		
		logger.info("Procurando uma pessoa, id = "+id);

		// Adicionando um lâmbida funcion para tratar exceção
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado!"));
		
		return DozerMapper.parseObject(entity, PersonVO.class);
		
	}
	
	public PersonVO create(PersonVO person) {
		logger.info("Estou no create!");

		var entity = DozerMapper.parseObject(person, Person.class);
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		return vo;
	}
	
	public PersonVOV2 createv2(PersonVOV2 person) {
		logger.info("Estou no create V2!");

		var entity = mapper.convertVOToEntity(person);
		
		var vo = mapper.convertEntityToVO(repository.save(entity));
		
		return vo;
	}	

	public PersonVO update(PersonVO person) {
		logger.info("Estou no update!");
		
		var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;

	}
	
	public void delete(Long id) {
		logger.info("Estou no delete!");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado!"));
		
		repository.delete(entity);
		
 	}	
	
	public List<PersonVO> findAll() {
		
		logger.info("Estou no findAll");
		
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}
	

}
