package com.safetynetalerts.api.model.DTO;

import java.util.List;

import com.safetynetalerts.api.model.Person;

public class ChildAlertDTO {
	
	private List<ChildDTO> childs;
	private List<Person> family;
	
	public List<Person> getFamily() {
		return family;
	}
	
	public void setFamily(List<Person> family) {
		this.family = family;
	}

	public List<ChildDTO> getChilds() {
		return childs;
	}

	public void setChilds(List<ChildDTO> childs) {
		this.childs = childs;
	}
}
