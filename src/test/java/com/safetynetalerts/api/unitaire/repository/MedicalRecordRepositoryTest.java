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
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.repository.MedicalRecordRepository;
import com.safetynetalerts.api.service.DataService;

@SpringBootTest
public class MedicalRecordRepositoryTest {
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	@MockBean
	private Data data;
	@MockBean
	private DataService dataService;
	private MedicalRecord medicalRecord;
	private List<MedicalRecord> medicalRecords;
	
	@BeforeEach
	public void setUpPerTest() {
		medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("Terrence");
		medicalRecord.setLastName("Meyer");
		medicalRecord.setBirthdate("25/05/2006");
		medicalRecord.setMedications(null);
		medicalRecord.setAllergies(null);
		
		medicalRecordRepository.deleteMedicalRecord(medicalRecord);
		
		medicalRecords = new ArrayList<MedicalRecord>();
		medicalRecords.add(medicalRecord);
	}
	
	@Test
	public void getMedicalRecordTest() {
		when(data.getMedicalRecords()).thenReturn(medicalRecords);
		
		assertTrue(medicalRecordRepository.getMedicalRecord().size() == 1);
	}
	
	@Test
	public void saveMedicalRecordTest() {
		medicalRecords.clear();
		when(data.getMedicalRecords()).thenReturn(medicalRecords);
		
		medicalRecordRepository.saveMedicalRecord(medicalRecord);
		
		assertTrue(medicalRecordRepository.getMedicalRecord().contains(medicalRecord));
	}
	
	@Test
	public void updateMedicalRecordTest() {
		when(data.getMedicalRecords()).thenReturn(medicalRecords);
		
		medicalRecord.setBirthdate("02/05/2001");
		
		medicalRecordRepository.updateMedicalRecord(25, medicalRecord);
		
		assertTrue(medicalRecordRepository.getMedicalRecord().contains(medicalRecord));
	}
	
	@Test
	public void deleteMedicalRecordTest() {
		when(data.getMedicalRecords()).thenReturn(medicalRecords);
		
		medicalRecordRepository.deleteMedicalRecord(medicalRecord);
		
		assertFalse(medicalRecordRepository.getMedicalRecord().contains(medicalRecord));
	}
}
