package com.safetynetalerts.api.service;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.api.model.Data;


import jakarta.annotation.PostConstruct;

@Service
public class DataService {

	private ObjectMapper objectMapper = new ObjectMapper();
	private Data tmpData;
	@Autowired
	private Data data;
	private static final Logger logger = LogManager.getLogger("DataService");
	
	@PostConstruct
	public void readJSON() {
	    logger.info("Starting to read data from JSON file: src/main/resources/data.json");

	    try {
	        // Lire le fichier JSON et assigner les données à tmpData
	        tmpData = objectMapper.readValue(new File("src/main/resources/data.json"), Data.class);
	        logger.debug("Successfully read JSON file: {}", "src/main/resources/data.json");

	        // Mise à jour des données dans l'objet global
	        data.setFirestations(tmpData.getFirestations());
	        data.setMedicalRecords(tmpData.getMedicalRecords());
	        data.setPersons(tmpData.getPersons());

	        logger.info("Data successfully loaded from JSON file");
	    } catch (IOException e) {
	        logger.error("Error reading JSON file: src/main/resources/data.json", e);
	    }
	}

	public void writeJSON() {
	    logger.info("Starting to write data to JSON file: src/main/resources/datatest.json");

	    try {
	        // Écrire les données dans le fichier JSON
	        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/datatest.json"), data);
	        logger.debug("Successfully wrote data to JSON file: {}", "src/main/resources/datatest.json");

	        logger.info("Data successfully saved to JSON file");
	    } catch (IOException e) {
	        logger.error("Error writing to JSON file: src/main/resources/datatest.json", e);
	    }
	}
}
