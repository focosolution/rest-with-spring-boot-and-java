package br.com.focosolution.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.focosolution.model.Person;

@Repository 
public interface PersonRepository extends JpaRepository<Person, Long> {}
