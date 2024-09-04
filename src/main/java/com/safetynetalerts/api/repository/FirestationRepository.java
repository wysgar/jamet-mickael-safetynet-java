package com.safetynetalerts.api.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.api.model.Data;
import com.safetynetalerts.api.model.Firestation;

import jakarta.annotation.PostConstruct;

import java.io.File;


@Repository
public class FirestationRepository {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private Data data;

	public void saveFirestation(Firestation firestation) throws FileNotFoundException, IOException {
		data.getFirestations().add(firestation);
	}

	public void updateFirestation(Firestation oldFirestation, Firestation updateFirestation) throws StreamReadException, DatabindException, IOException {
		data.getFirestations().remove(oldFirestation);
		data.getFirestations().add(updateFirestation);
	}

	public void deleteFirestation(Firestation firestation) throws StreamReadException, DatabindException, IOException {
		data.getFirestations().remove(firestation);
	}
	
	@PostConstruct
	public void readJSON() throws StreamReadException, DatabindException, IOException {
		data = objectMapper.readValue(new File("src/main/resources/data.json"), Data.class);
	}

	public List<Firestation> getFirestation() {
		return data.getFirestations();
	}
}
