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

/**
 * Service responsible for handling data loading and saving operations from/to JSON files.
 * It reads the initial data from a JSON file during the application's startup and provides a method
 * to write the current state of the data back into a JSON file.
 */
@Service
public class DataService {

	private ObjectMapper objectMapper = new ObjectMapper();
	private Data tmpData;
	@Autowired
	private Data data;
	private static final Logger logger = LogManager.getLogger("DataService");
	
	/**
     * Reads the data from a predefined JSON file (src/main/resources/data.json) when the application starts.
     * The data is parsed and loaded into the global `Data` object.
     * 
     * This method is called automatically after the bean's properties have been initialized by Spring.
     * In case of an error while reading the JSON file, an error message is logged.
     */
	@PostConstruct
	public void readJSON() {
	    logger.info("Starting to read data from JSON file: src/main/resources/data.json");

	    try {
	        // Read JSON file and assign data to tmpData
	        tmpData = objectMapper.readValue(new File("src/main/resources/data.json"), Data.class);
	        logger.debug("Successfully read JSON file: {}", "src/main/resources/data.json");

	        // Updating data in global object
	        data.setFirestations(tmpData.getFirestations());
	        data.setMedicalRecords(tmpData.getMedicalRecords());
	        data.setPersons(tmpData.getPersons());

	        logger.info("Data successfully loaded from JSON file");
	    } catch (IOException e) {
	        logger.error("Error reading JSON file: src/main/resources/data.json", e);
	    }
	}

	/**
     * Writes the current state of the application's data into a predefined JSON file (src/main/resources/data.json).
     * The data is written in a pretty-printed format to enhance readability.
     * 
     * In case of an error while writing to the JSON file, an error message is logged.
     */
	public void writeJSON() {
	    logger.info("Starting to write data to JSON file: src/main/resources/datatest.json");

	    try {
	        // Write data to JSON file
	        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/data.json"), data);
	        logger.debug("Successfully wrote data to JSON file: {}", "src/main/resources/datatest.json");

	        logger.info("Data successfully saved to JSON file");
	    } catch (IOException e) {
	        logger.error("Error writing to JSON file: src/main/resources/datatest.json", e);
	    }
	}
}
