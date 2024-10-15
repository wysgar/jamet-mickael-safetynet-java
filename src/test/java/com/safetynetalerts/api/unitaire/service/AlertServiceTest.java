package com.safetynetalerts.api.unitaire.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.model.DTO.ChildAlertDTO;
import com.safetynetalerts.api.model.DTO.FireDTO;
import com.safetynetalerts.api.model.DTO.PersonInfoLastNameDTO;
import com.safetynetalerts.api.service.AlertService;
import com.safetynetalerts.api.service.FirestationService;
import com.safetynetalerts.api.service.MedicalRecordService;
import com.safetynetalerts.api.service.PersonService;

@SpringBootTest
public class AlertServiceTest {
	@Autowired
	private AlertService alertService;
	@MockBean
	private PersonService personService;
	@MockBean
	private FirestationService firestationService;
	@MockBean
	private MedicalRecordService medicalRecordService;
	private Firestation firestation;
	private List<Firestation> firestations;
	private List<MedicalRecord> medicalRecords;
	private Person person;
	private List<Person> persons;
	
	@BeforeEach
	public void setUpPerTest() {
		firestation = new Firestation();
		firestation.setAddress("100 rue");
		firestation.setStation(3);
		
		firestations = new ArrayList<Firestation>();
		firestations.add(firestation);
		
		medicalRecords = new ArrayList<MedicalRecord>();
		
		person = new Person();
		person.setFirstName("Terrence");
		person.setLastName("Meyer");
		person.setPhone("000-000-0000");
		person.setAddress("100 rue");
		person.setCity("Montpellier");
		person.setEmail("terrence.meyer@gmail.com");
		person.setZip(34000);
		
		persons = new ArrayList<Person>();
		persons.add(person);
	}
	
	@Test
	public void getChildPerAddressTestIfNotChild() {
		when(personService.getPerson()).thenReturn(persons);
		when(medicalRecordService.getMedicalRecord()).thenReturn(medicalRecords);
		
		ChildAlertDTO result = alertService.getChildPerAddress("29 15th St");
		
		assertTrue(result.getChilds().isEmpty());
		assertTrue(result.getFamily().isEmpty());
	}
	
	@Test
	public void getPersonPerAddressIfAddressNotHaveStation() {
		when(firestationService.getFirestation()).thenReturn(firestations);
		when(medicalRecordService.getMedicalRecord()).thenReturn(medicalRecords);
		when(personService.getPerson()).thenReturn(persons);
		
		FireDTO result = alertService.getPersonPerAddress("100 rue Firania");
		
		assertTrue(result.getPersons() == null);
		assertTrue(result.getStation() == 0);
	}
	
	@Test
	public void getPersonPerAddressIfMedicalRecordIsNull() {
		when(firestationService.getFirestation()).thenReturn(firestations);
		when(medicalRecordService.getMedicalRecord()).thenReturn(medicalRecords);
		when(personService.getPerson()).thenReturn(persons);
		
		FireDTO result = alertService.getPersonPerAddress("100 rue");
		
		assertTrue(result.getPersons().isEmpty());
		assertTrue(result.getStation() == 3);
	}
	
	@Test
	public void getInfoPerPersonTestIfMedicalRecordIsNull() {
		when(personService.getPerson()).thenReturn(persons);
		when(medicalRecordService.getMedicalRecord()).thenReturn(medicalRecords);
		
		List<PersonInfoLastNameDTO> result = alertService.getInfoPerPerson("Meyer");
		
		assertTrue(result.isEmpty());
	}
}
