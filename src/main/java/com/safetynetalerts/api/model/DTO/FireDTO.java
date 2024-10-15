package com.safetynetalerts.api.model.DTO;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing information related to a fire incident.
 * 
 * This class encapsulates details of persons affected by a fire incident
 * associated with a specific fire station.
 */
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
