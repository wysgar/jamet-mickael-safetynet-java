package com.safetynetalerts.api.model.DTO;

/**
 * Data Transfer Object (DTO) representing a child.
 * 
 * This class encapsulates the details of a child, including their
 * first name, last name, and age. It is used for data transfer 
 * purposes within the application, particularly in scenarios related 
 * to child information.
 */
public class ChildDTO {
	private String firstName;
	private String lastName;
	private int age;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
