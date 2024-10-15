package com.safetynetalerts.api.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynetalerts.api.model.Data;
import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.service.DataService;

/**
 * Repository class for managing {@link Firestation} data.
 * This class provides methods to retrieve, save, update, and delete firestation records.
 * It also handles writing firestation data to JSON files.
 */
@Repository
public class FirestationRepository {
	
	@Autowired
	private Data data;
	@Autowired
	private DataService dataService;
	private static final Logger logger = LogManager.getLogger("FirestationRepository");

	/**
     * Retrieves the list of firestations from the data source.
     *
     * @return a list of {@link Firestation}, or {@code null} if an error occurs
     */
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

	/**
     * Saves a new firestation to the data source and writes the updated data to JSON.
     *
     * @param firestation the {@link Firestation} to save
     */
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

	/**
     * Updates an existing firestation at the specified index and writes the updated data to JSON.
     *
     * @param index the index of the firestation to update
     * @param firestation the updated {@link Firestation} object
     */
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

	/**
     * Deletes the specified firestation from the data source and writes the updated data to JSON.
     *
     * @param firestation the {@link Firestation} to delete
     */
	public void deleteFirestation(Firestation firestation) {
	    logger.info("Attempting to delete firestation: {}", firestation);
	    try {
	        data.getFirestations().remove(firestation);
	        writeJSON();
	        logger.info("Successfully deleted firestation: {}", firestation);
	    } catch (Exception e) {
	        logger.error("Error deleting firestation: {}", firestation, e);
	    }
	}
	
	/**
     * Writes the current firestation data to a JSON file.
     */
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
