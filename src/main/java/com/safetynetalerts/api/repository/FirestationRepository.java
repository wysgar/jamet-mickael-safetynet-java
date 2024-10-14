package com.safetynetalerts.api.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynetalerts.api.model.Data;
import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.service.DataService;

@Repository
public class FirestationRepository {
	
	@Autowired
	private Data data;
	@Autowired
	private DataService dataService;
	private static final Logger logger = LogManager.getLogger("FirestationRepository");

	public List<Firestation> getFirestation() {
	    logger.info("Fetching list of firestations.");
	    try {
	        List<Firestation> firestations = data.getFirestations();
	        logger.debug("Retrieved {} firestations", firestations.size());
	        return firestations;
	    } catch (Exception e) {
	        logger.error("Error fetching firestations.", e);
	    }
	    return null;
	}

	public void saveFirestation(Firestation firestation) {
	    logger.info("Attempting to save firestation: {}", firestation);
	    try {
	        data.getFirestations().add(firestation);
	        writeJSON();
	        logger.info("Successfully saved firestation: {}", firestation);
	    } catch (Exception e) {
	        logger.error("Error saving firestation: {}", firestation, e);
	    }
	}

	public void updateFirestation(int index, Firestation firestation) {
	    logger.info("Attempting to update firestation at index {} with: {}", index, firestation);
	    try {
	        data.getFirestations().set(index, firestation);
	        writeJSON();
	        logger.info("Successfully updated firestation at index {}.", index);
	    } catch (Exception e) {
	        logger.error("Error updating firestation at index {} with: {}", index, firestation, e);
	    }
	}

	public void deleteFirestation(Firestation firestation) {
	    logger.info("Attempting to delete firestation: {}", firestation);
	    try {
	        if (data.getFirestations().remove(firestation)) {
	            writeJSON();
	            logger.info("Successfully deleted firestation: {}", firestation);
	        } else {
	            logger.error("Firestation not found: {}", firestation);
	        }
	    } catch (Exception e) {
	        logger.error("Error deleting firestation: {}", firestation, e);
	    }
	}
	
	public void writeJSON() {
	    logger.info("Writing firestations data to JSON.");
	    try {
	        dataService.writeJSON();
	        logger.info("Successfully wrote firestations data to JSON.");
	    } catch (Exception e) {
	        logger.error("Error writing firestations data to JSON.", e);
	    }
	}
}
