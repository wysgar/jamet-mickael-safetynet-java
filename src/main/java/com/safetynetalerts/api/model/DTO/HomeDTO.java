package com.safetynetalerts.api.model.DTO;

import java.util.List;

public class HomeDTO {
	private List<PersonMedicalRecordDTO> home;
	
	public List<PersonMedicalRecordDTO> getHome() {
		return home;
	}
	public void setHome(List<PersonMedicalRecordDTO> persons) {
		this.home = persons;
	}
}
