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
import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.repository.FirestationRepository;
import com.safetynetalerts.api.service.DataService;

@SpringBootTest
public class FirestationRepositoryTest {
	
	@Autowired
	private FirestationRepository firestationRepository;
	@MockBean
	private Data data;
	@MockBean
	private DataService dataService;
	private Firestation firestation;
	private List<Firestation> firestations;
	
	@BeforeEach
	public void setUpPerTest() {
		firestation = new Firestation();
		firestation.setAddress("100 rue");
		firestation.setStation(3);
		
		firestationRepository.deleteFirestation(firestation);
		
		firestations = new ArrayList<Firestation>();
		firestations.add(firestation);
	}
	
	@Test
	public void getFirestationTest() {
		when(data.getFirestations()).thenReturn(firestations);
		
		assertTrue(firestationRepository.getFirestation().size() == 1);
	}
	
	@Test
	public void saveFirestationTest() {
		firestations.clear();
		when(data.getFirestations()).thenReturn(firestations);
		
		firestationRepository.saveFirestation(firestation);
		
		assertTrue(firestationRepository.getFirestation().contains(firestation));
	}
	
	@Test
	public void updateFirestationTest() {
		when(data.getFirestations()).thenReturn(firestations);
		
		firestation.setStation(4);
		
		firestationRepository.updateFirestation(13, firestation);
		
		assertTrue(firestationRepository.getFirestation().contains(firestation));
	}
	
	@Test
	public void deleteFirestationTest() {
		when(data.getFirestations()).thenReturn(firestations);
		
		firestationRepository.deleteFirestation(firestation);
		
		assertFalse(firestationRepository.getFirestation().contains(firestation));
	}
}
