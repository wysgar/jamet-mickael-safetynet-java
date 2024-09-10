package com.safetynetalerts.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	public List<Person> getPerson() {
		return personRepository.getPerson();
	}

	public void savePerson(Person person) {
		personRepository.savePerson(person);
	}

	public void updatePerson(Person oldPerson, Person newPerson) {
		personRepository.updatePerson(oldPerson, newPerson);
	}

	public void deletePerson(Person person) {
		personRepository.deletePerson(person);
	}

}
