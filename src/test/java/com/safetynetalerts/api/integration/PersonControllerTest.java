package com.safetynetalerts.api.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.PersonRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

	@Autowired
	public MockMvc mockMvc;
	@Autowired
	private PersonRepository personRepository;
	private Person person;
	
	@BeforeEach
	public void createPerson() {
		person = new Person();
		person.setFirstName("Terrence");
		person.setLastName("Meyer");
		person.setPhone("000-000-0000");
		person.setAddress("10 rue firania");
		person.setCity("Montpellier");
		person.setEmail("terrence.meyer@gmail.com");
		person.setZip(34000);
	}
	
	@Test
	public void testSavePerson() throws Exception {
		mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(person))).andExpect(status().isOk());
		assertTrue(personRepository.getPerson().contains(person));
	}
	
	@Test
	public void testUpdatePerson() throws Exception {
		testSavePerson();
		person.setPhone("111-000-0000");
		
		mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(person))).andExpect(status().isOk());
		assertTrue(personRepository.getPerson().contains(person));
	}
	
	@Test
	public void testDeletePerson() throws JsonProcessingException, Exception {
		testSavePerson();
		
		mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(person))).andExpect(status().isOk());
		assertFalse(personRepository.getPerson().contains(person));
	}
}
