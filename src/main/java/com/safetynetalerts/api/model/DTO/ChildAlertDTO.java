package com.safetynetalerts.api.model.DTO;

import java.util.List;

import com.safetynetalerts.api.model.Person;

public class ChildAlertDTO {
	
	private String firstName;
	private String lastName;
	private int age;
	private List<Person> family;
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public int getAge() {
		return age;
	}
	public List<Person> getFamily() {
		return family;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
	public void setAge(int age) {
		this.age = age;
	}
	public void setFamily(List<Person> family) {
		this.family = family;
	}
}
