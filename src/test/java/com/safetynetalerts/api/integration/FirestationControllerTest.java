package com.safetynetalerts.api.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.repository.FirestationRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTest {
	
	@Autowired
	public MockMvc mockMvc;
	@Autowired
	private FirestationRepository firestationRepository;
	private Firestation firestation;
	
	@BeforeEach
	public void createrFirestation() {
		firestation = new Firestation();
		firestation.setAddress("100 rue");
		firestation.setStation(3);
	}
	
	@Test
	public void testGetPersonPerStation() throws Exception {
		mockMvc.perform(get("/firestation").param("stationNumber", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.countAdult", is(5)))
			.andExpect(jsonPath("$.countChild", is(1)));
	}
	
	@Test
	public void testSaveFirestation() throws Exception {
		mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(firestation))).andExpect(status().isOk());
		assertTrue(firestationRepository.getFirestation().contains(firestation));
	}
	
	@Test
	public void testUpdateFirestation() throws Exception {
		testSaveFirestation();
		firestation.setStation(4);
		mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(firestation))).andExpect(status().isOk());
		assertTrue(firestationRepository.getFirestation().contains(firestation));
	}
	
	@Test
	public void testDeleteFirestation() throws JsonProcessingException, Exception {
		testSaveFirestation();
		mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(firestation))).andExpect(status().isOk());
		assertFalse(firestationRepository.getFirestation().contains(firestation));
	}
}
