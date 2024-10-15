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
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.repository.MedicalRecordRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

	@Autowired
	public MockMvc mockMvc;
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	private MedicalRecord medicalRecord;
	
	@BeforeEach
	public void createMedicalRecord() {
		medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("Terrence");
		medicalRecord.setLastName("Meyer");
		medicalRecord.setBirthdate("25/05/2006");
		medicalRecord.setMedications(null);
		medicalRecord.setAllergies(null);
	}
	
	@Test
	public void testSaveMedicalRecord() throws Exception {
		mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(medicalRecord))).andExpect(status().isOk());
		assertTrue(medicalRecordRepository.getMedicalRecord().contains(medicalRecord));
	}
	
	@Test
	public void testUpdateMedicalRecord() throws Exception {
		testSaveMedicalRecord();
		medicalRecord.setBirthdate("25/05/2001");
		
		mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(medicalRecord))).andExpect(status().isOk());
		assertTrue(medicalRecordRepository.getMedicalRecord().contains(medicalRecord));
	}
	
	@Test
	public void testDeleteMedicalRecord() throws JsonProcessingException, Exception {
		testSaveMedicalRecord();
		
		mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(medicalRecord))).andExpect(status().isOk());
		assertFalse(medicalRecordRepository.getMedicalRecord().contains(medicalRecord));
	}
}
