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

import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.repository.FirestationRepository;
import com.safetynetalerts.api.service.FirestationService;

@SpringBootTest
public class FirestationServiceTest {
	
	@Autowired
	private FirestationService firestationService;
	@MockBean
	private FirestationRepository firestationRepository;
	private Firestation firestation;
	private List<Firestation> firestations;
	
	@BeforeEach
	public void setUpPerTest() {
		firestation = new Firestation();
		firestation.setAddress("100 rue");
		firestation.setStation(3);
		
		firestations = new ArrayList<Firestation>();
		firestations.add(firestation);
	}
	
	@Test
	public void getPersonTest() {
		when(firestationRepository.getFirestation()).thenReturn(firestations);
		
		firestationService.getFirestation();
		
		verify(firestationRepository, Mockito.times(1)).getFirestation();
		assertTrue(!firestationService.getFirestation().isEmpty());
	}
	
	@Test
	public void saveFirestationTest() {
		firestations.clear();
		when(firestationRepository.getFirestation()).thenReturn(firestations);
		
		firestationService.saveFirestation(firestation);
		
		verify(firestationRepository, Mockito.times(1)).saveFirestation(any(Firestation.class));
	}
	
	@Test
	public void saveFirestationTestIfExist() {
		when(firestationRepository.getFirestation()).thenReturn(firestations);
		
		firestationService.saveFirestation(firestation);
		
		verify(firestationRepository, Mockito.times(0)).saveFirestation(any(Firestation.class));
	}
	
	@Test
	public void updateFirestationTest() {
		firestation.setStation(4);
		when(firestationRepository.getFirestation()).thenReturn(firestations);
		
		firestationService.updateFirestation(firestation);
		
		verify(firestationRepository, Mockito.times(1)).updateFirestation(anyInt(), any(Firestation.class));
		
		firestationService.deleteFirestation(firestation);
	}
	
	@Test
	public void updateFirestationTestIfNotExist() {
		firestations.clear();
		when(firestationRepository.getFirestation()).thenReturn(firestations);
		
		firestationService.updateFirestation(firestation);
		
		verify(firestationRepository, Mockito.times(0)).updateFirestation(anyInt(), any(Firestation.class));
	}
	
	@Test
	public void deleteFirestationTest() {
		when(firestationRepository.getFirestation()).thenReturn(firestations);
		
		firestationService.deleteFirestation(firestation);
		
		verify(firestationRepository, Mockito.times(1)).deleteFirestation(any(Firestation.class));
	}
	
	@Test
	public void deleteFirestationTestIfNotExist() {
		firestations.clear();
		when(firestationRepository.getFirestation()).thenReturn(firestations);
		
		firestationService.deleteFirestation(firestation);
		
		verify(firestationRepository, Mockito.times(0)).deleteFirestation(any(Firestation.class));
	}
}
