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

import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.repository.MedicalRecordRepository;
import com.safetynetalerts.api.service.MedicalRecordService;

@SpringBootTest
public class MedicalRecordServiceTest {
	@Autowired
	private MedicalRecordService medicalRecordService;
	@MockBean
	private MedicalRecordRepository medicalRecordRepository;
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
		
		medicalRecords = new ArrayList<MedicalRecord>();
		medicalRecords.add(medicalRecord);
	}
	
	@Test
	public void getPersonTest() {
		when(medicalRecordRepository.getMedicalRecord()).thenReturn(medicalRecords);
		
		medicalRecordService.getMedicalRecord();
		
		verify(medicalRecordRepository, Mockito.times(1)).getMedicalRecord();
		assertTrue(!medicalRecordService.getMedicalRecord().isEmpty());
	}
	
	@Test
	public void saveMedicalRecordTest() {
		medicalRecords.clear();
		when(medicalRecordRepository.getMedicalRecord()).thenReturn(medicalRecords);
		
		medicalRecordService.saveMedicalRecord(medicalRecord);
		
		verify(medicalRecordRepository, Mockito.times(1)).saveMedicalRecord(any(MedicalRecord.class));
	}
	
	@Test
	public void saveMedicalRecordTestIfExist() {
		when(medicalRecordRepository.getMedicalRecord()).thenReturn(medicalRecords);
		
		medicalRecordService.saveMedicalRecord(medicalRecord);
		
		verify(medicalRecordRepository, Mockito.times(0)).saveMedicalRecord(any(MedicalRecord.class));
	}
	
	@Test
	public void updateMedicalRecordTest() {
		medicalRecord.setBirthdate("25/05/2001");
		when(medicalRecordRepository.getMedicalRecord()).thenReturn(medicalRecords);
		
		medicalRecordService.updateMedicalRecord(medicalRecord);
		
		verify(medicalRecordRepository, Mockito.times(1)).updateMedicalRecord(anyInt(), any(MedicalRecord.class));
	}
	
	@Test
	public void updateMedicalRecordTestIfNotExist() {
		medicalRecords.clear();
		when(medicalRecordRepository.getMedicalRecord()).thenReturn(medicalRecords);
		
		medicalRecordService.updateMedicalRecord(medicalRecord);
		
		verify(medicalRecordRepository, Mockito.times(0)).updateMedicalRecord(anyInt(), any(MedicalRecord.class));
	}
	
	@Test
	public void deleteMedicalRecordTest() {
		when(medicalRecordRepository.getMedicalRecord()).thenReturn(medicalRecords);
		
		medicalRecordService.deleteMedicalRecord(medicalRecord);
		
		verify(medicalRecordRepository, Mockito.times(1)).deleteMedicalRecord(any(MedicalRecord.class));
	}
	
	@Test
	public void deleteMedicalRecordTestIfNotExist() {
		medicalRecords.clear();
		when(medicalRecordRepository.getMedicalRecord()).thenReturn(medicalRecords);
		
		medicalRecordService.deleteMedicalRecord(medicalRecord);
		
		verify(medicalRecordRepository, Mockito.times(0)).deleteMedicalRecord(any(MedicalRecord.class));
	}
}
