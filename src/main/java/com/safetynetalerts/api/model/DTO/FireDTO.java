package com.safetynetalerts.api.model.DTO;

import java.util.List;

public class FireDTO {
	private List<PersonMedicalRecordDTO> persons;
	private int station;
	
	public List<PersonMedicalRecordDTO> getPersons() {
		return persons;
	}
	public int getStation() {
		return station;
	}
	
	public void setPersons(List<PersonMedicalRecordDTO> persons) {
		this.persons = persons;
	}
	public void setStation(int station) {
		this.station = station;
	}
}
