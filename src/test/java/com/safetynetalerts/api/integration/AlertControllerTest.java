package com.safetynetalerts.api.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AlertControllerTest {

	@Autowired
	public MockMvc mockMvc;
	
	@Test
	public void testGetChildPerAddress() throws Exception {
		mockMvc.perform(get("/childAlert").param("address", "1509 Culver St"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.childs.length()", is(2)))
			.andExpect(jsonPath("$.family.length()", is(3)));
	}
	
	@Test
	public void testGetPhonePerStation() throws Exception {
		mockMvc.perform(get("/phoneAlert").param("firestation", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.phone.length()", is(4)));
	}
	
	@Test
	public void testGetPersonPerAddress() throws Exception {
		mockMvc.perform(get("/fire").param("address", "1509 Culver St"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.persons.length()", is(5)))
			.andExpect(jsonPath("$.station", is(3)));
	}
	
	@Test
	public void testGetHomePerStation() throws Exception {
		mockMvc.perform(get("/flood/stations").param("stations", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].stationNumber", is(1)))
			.andExpect(jsonPath("$[0].homes.length()", is(3)));
	}
	
	@Test
	public void testGetHomePerStationWith2Param() throws Exception {
		mockMvc.perform(get("/flood/stations").param("stations", "1, 2"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].stationNumber", is(1)))
			.andExpect(jsonPath("$[0].homes.length()", is(3)))
			.andExpect(jsonPath("$[1].stationNumber", is(2)))
			.andExpect(jsonPath("$[1].homes.length()", is(3)));
	}
	
	@Test
	public void testGetInfoPerPerson() throws Exception {
		mockMvc.perform(get("/personInfolastName").param("lastName", "Boyd"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()", is(6)));
	}
	
	@Test
	public void testGetEmail() throws Exception {
		mockMvc.perform(get("/communityEmail").param("city", "Culver"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()", is(15)));
	}
}
