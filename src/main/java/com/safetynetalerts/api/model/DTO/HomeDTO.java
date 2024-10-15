package com.safetynetalerts.api.model.DTO;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a home containing a list of persons
 * along with their medical records.
 * 
 * This class encapsulates a collection of {@link PersonMedicalRecordDTO} objects,
 * which provide details about individuals residing in a home, including their
 * medical information. It is used for transferring home-related data between
 * different layers of the application.
 */
public class HomeDTO {
	private List<PersonMedicalRecordDTO> home;
	
	public List<PersonMedicalRecordDTO> getHome() {
		return home;
	}
	public void setHome(List<PersonMedicalRecordDTO> persons) {
		this.home = persons;
	}
}
