package com.safetynetalerts.api.model.DTO;

import java.util.List;

public class PersonMedicalRecordDTO {
	private String lastName;
	private String phone;
	private int age;
	private List<String> medication;
	private List<String> allergie;
	
	public int getAge() {
		return age;
	}
	public List<String> getAllergie() {
		return allergie;
	}
	public String getLastName() {
		return lastName;
	}
	public List<String> getMedication() {
		return medication;
	}
	public String getPhone() {
		return phone;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	public void setAllergie(List<String> allergie) {
		this.allergie = allergie;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setMedication(List<String> medication) {
		this.medication = medication;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
