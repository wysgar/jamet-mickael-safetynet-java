package com.safetynetalerts.api.model;

import java.util.List;
import java.util.Objects;

public class MedicalRecord {
	private String firstName;
	private String lastName;
	private String birthdate;
	private List<String> medications;
	private List<String> allergies;
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public List<String> getMedications() {
		return medications;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setBirthdate(String birthday) {
		this.birthdate = birthday;
	}
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(allergies, birthdate, firstName, lastName, medications);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalRecord other = (MedicalRecord) obj;
		return Objects.equals(allergies, other.allergies) && Objects.equals(birthdate, other.birthdate)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(medications, other.medications);
	}
}
