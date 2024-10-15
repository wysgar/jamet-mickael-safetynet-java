package com.safetynetalerts.api.unitaire.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynetalerts.api.model.Data;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.PersonRepository;
import com.safetynetalerts.api.service.DataService;

@SpringBootTest
public class PersonRepositoryTest {
	
	@Autowired
	private PersonRepository personRepository;
	@MockBean
	private Data data;
	@MockBean
	private DataService dataService;
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
		
		personRepository.deletePerson(person);
		
		persons = new ArrayList<Person>();
		persons.add(person);
	}
	
	@Test
	public void getFirestationTest() {
		when(data.getPersons()).thenReturn(persons);
		
		assertTrue(personRepository.getPerson().size() == 1);
	}
	
	@Test
	public void saveFirestationTest() {
		persons.clear();
		when(data.getPersons()).thenReturn(persons);
		
		personRepository.savePerson(person);
		
		assertTrue(personRepository.getPerson().contains(person));
	}
	
	@Test
	public void updateFirestationTest() {
		when(data.getPersons()).thenReturn(persons);

		person.setCity("Firania");
		
		personRepository.updatePerson(0, person);
		
		assertTrue(personRepository.getPerson().contains(person));
	}
	
	@Test
	public void deleteFirestationTest() {
		when(data.getPersons()).thenReturn(persons);
		
		personRepository.deletePerson(person);
		
		assertFalse(personRepository.getPerson().contains(person));
	}
}
