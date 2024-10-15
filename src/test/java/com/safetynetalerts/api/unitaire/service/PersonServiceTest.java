package com.safetynetalerts.api.unitaire.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.PersonRepository;
import com.safetynetalerts.api.service.PersonService;

@SpringBootTest
public class PersonServiceTest {
	
	@Autowired
	private PersonService personService;
	@MockBean
	private PersonRepository personRepository;
	private Person person;
	private List<Person> persons;
	
	@BeforeEach
	public void setUpPerTest() {
		person = new Person();
		person.setFirstName("Terrence");
		person.setLastName("Meyer");
		person.setPhone("000-000-0000");
		person.setAddress("10 rue firania");
		person.setCity("Montpellier");
		person.setEmail("terrence.meyer@gmail.com");
		person.setZip(34000);
		
		persons = new ArrayList<Person>();
		persons.add(person);
	}
	
	@Test
	public void getPersonTest() {
		when(personRepository.getPerson()).thenReturn(persons);
		
		personService.getPerson();
		
		verify(personRepository, Mockito.times(1)).getPerson();
		assertTrue(!personService.getPerson().isEmpty());
	}
	
	@Test
	public void savePersonTest() {
		persons.clear();
		when(personRepository.getPerson()).thenReturn(persons);
		
		personService.savePerson(person);

		verify(personRepository, Mockito.times(1)).savePerson(any(Person.class));
	}
	
	@Test
	public void savePersonTestIfExist() {
		when(personRepository.getPerson()).thenReturn(persons);
		
		personService.savePerson(person);

		verify(personRepository, Mockito.times(0)).savePerson(any(Person.class));
	}
	
	@Test
	public void updatePersonTest() {
		person.setCity("Firania");
		when(personRepository.getPerson()).thenReturn(persons);
		
		personService.updatePerson(person);
		
		verify(personRepository, Mockito.times(1)).updatePerson(anyInt(), any(Person.class));
	}
	
	@Test
	public void updatePersonTestIfNotExist() {
		persons.clear();
		when(personRepository.getPerson()).thenReturn(persons);
		
		personService.updatePerson(person);
		
		verify(personRepository, Mockito.times(0)).updatePerson(anyInt(), any(Person.class));
	}
	
	@Test
	public void deletePersonTest() {
		when(personRepository.getPerson()).thenReturn(persons);
		
		personService.deletePerson(person);
		
		verify(personRepository, Mockito.times(1)).deletePerson(any(Person.class));
	}
	
	@Test
	public void deletePersonTestIfNotExist() {
		persons.clear();
		when(personRepository.getPerson()).thenReturn(persons);
		
		personService.deletePerson(person);
		
		verify(personRepository, Mockito.times(0)).deletePerson(any(Person.class));
	}
}
