package com.safetynetalerts.api.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.api.model.Data;


import jakarta.annotation.PostConstruct;

@Service
public class DataService {

	private ObjectMapper objectMapper = new ObjectMapper();
	private Data tmpData;
	@Autowired
	private Data data;
	
	@PostConstruct
	public void readJSON() throws StreamReadException, DatabindException, IOException {
		tmpData = objectMapper.readValue(new File("src/main/resources/data.json"), Data.class);
		data.setFirestations(tmpData.getFirestations());
		data.setMedicalRecords(tmpData.getMedicalRecords());
		data.setPersons(tmpData.getPersons());
	}
}
