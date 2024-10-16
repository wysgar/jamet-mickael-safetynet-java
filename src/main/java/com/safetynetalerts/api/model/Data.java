package com.safetynetalerts.api.model;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a data set containing information about
 * persons, firestations, and medical records.
 * 
 * This class is used to encapsulate the data loaded
 * from a JSON file, allowing easy access to the different
 * entities within the application.
 */
@Repository
public class Data {
	
	@JsonProperty("persons")
	private List<Person> persons;
	@JsonProperty("firestations")
	private List<Firestation> firestations;
	@JsonProperty("medicalrecords")
	private List<MedicalRecord> medicalRecords;

	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	public List<Firestation> getFirestations() {
		return firestations;
	}
	public void setFirestations(List<Firestation> firestations) {
		this.firestations = firestations;
	}
	
	public List<MedicalRecord> getMedicalRecords() {
		return medicalRecords;
	}
	public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}
}
