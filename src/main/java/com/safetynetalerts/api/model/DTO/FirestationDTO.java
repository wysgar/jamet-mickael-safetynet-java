package com.safetynetalerts.api.model.DTO;

import java.util.List;

public class FirestationDTO {
	private List<PersonDTO> persons;
	private int countAdult;
	private int countChild;
	
	public int getCountChild() {
		return countChild;
	}
	public int getCountAdult() {
		return countAdult;
	}
	public List<PersonDTO> getPersons() {
		return persons;
	}
	
	public void setCountChild(int countChild) {
		this.countChild = countChild;
	}
	public void setCountAdult(int countAdult) {
		this.countAdult = countAdult;
	}
	public void setPersons(List<PersonDTO> persons) {
		this.persons = persons;
	}
}
