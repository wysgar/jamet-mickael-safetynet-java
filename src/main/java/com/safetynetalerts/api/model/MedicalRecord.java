package com.safetynetalerts.api.model;

public class MedicalRecord {
	private String firstName;
	private String lastName;
	private String birthdate;
	private String[] medications;
	private String[] allergies;
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public String[] getMedications() {
		return medications;
	}
	public String[] getAllergies() {
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
	public void setMedications(String[] medications) {
		this.medications = medications;
	}
	public void setAllergies(String[] allergies) {
		this.allergies = allergies;
	}
}
