package com.safetynetalerts.api.model.DTO;

import java.util.List;

public class PersonInfoLastNameDTO {
	private String lastName;
	private String address;
	private int age;
	private String email;
	private List<String> medication;
	private List<String> allergie;
	
	public String getAddress() {
		return address;
	}
	public int getAge() {
		return age;
	}
	public List<String> getAllergie() {
		return allergie;
	}
	public String getEmail() {
		return email;
	}
	public String getLastName() {
		return lastName;
	}
	public List<String> getMedication() {
		return medication;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setAllergie(List<String> allergie) {
		this.allergie = allergie;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setMedication(List<String> medication) {
		this.medication = medication;
	}
}
