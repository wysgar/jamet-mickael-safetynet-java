package com.safetynetalerts.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynetalerts.api.model.Data;
import com.safetynetalerts.api.model.Person;

@Repository
public class PersonRepository {

	@Autowired
	private Data data;
	
	public List<Person> getPerson() {
		return data.getPersons();
	}

	public void savePerson(Person person) {
		data.getPersons().add(person);
	}
	
	public void updatePerson(Person oldPerson, Person newPerson) {
		data.getPersons().remove(oldPerson);
		data.getPersons().add(newPerson);
	}

	public void deletePerson(Person person) {
		data.getPersons().remove(person);
	}
}
