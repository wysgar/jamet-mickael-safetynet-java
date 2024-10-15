package com.safetynetalerts.api.model.DTO;

import java.util.List;

/**
 * Data Transfer Object (DTO) for representing phone alert information.
 * 
 * This class encapsulates the details related to phone numbers for 
 * alert notifications. It is used to hold a list of phone numbers 
 * that need to be notified in case of an emergency.
 */
public class PhoneAlertDTO {
	private List<String> phone;
	
	public List<String> getPhone() {
		return phone;
	}
	public void setPhone(List<String> phone) {
		this.phone = phone;
	}
}
