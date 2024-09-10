package com.safetynetalerts.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.service.PersonService;

@RestController("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping
	public List<Person> getPerson() {
		return personService.getPerson();
	}
	
	@PostMapping
	public void savePerson(@RequestBody Person person) {
		personService.savePerson(person);
	}
	
	@PutMapping
	public void updatePerson(@RequestBody Person person) {
		personService.updatePerson(person, person);
	}
	
	@DeleteMapping
	public void deletePerson(@RequestBody Person person) {
		personService.deletePerson(person);
	}
}
