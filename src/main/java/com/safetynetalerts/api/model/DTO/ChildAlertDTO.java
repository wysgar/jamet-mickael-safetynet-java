package com.safetynetalerts.api.model.DTO;

import java.util.List;

import com.safetynetalerts.api.model.Person;

/**
 * Data Transfer Object (DTO) for representing child alert information.
 * 
 * This class encapsulates the details regarding children and their families
 * as part of the safety net alerts system. It contains a list of children
 * and a list of family members associated with those children.
 */
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
