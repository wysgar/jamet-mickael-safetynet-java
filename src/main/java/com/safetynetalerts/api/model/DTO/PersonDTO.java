package com.safetynetalerts.api.model.DTO;

/**
 * Data Transfer Object (DTO) representing a person's basic information.
 * 
 * This class encapsulates essential details such as the person's first name, last name,
 * address, and phone number. It is used for transferring personal information between
 * layers of the application, particularly in response and request objects.
 */
public class PersonDTO {
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	
	public String getAddress() {
		return address;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPhone() {
		return phone;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
