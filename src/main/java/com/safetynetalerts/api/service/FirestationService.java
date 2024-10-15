package com.safetynetalerts.api.service;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.repository.FirestationRepository;

/**
 * Service responsible for handling operations related to firestations.
 * Provides methods to retrieve, save, update, and delete firestations.
 */
@Service
public class FirestationService {
	
	@Autowired
	private FirestationRepository firestationRepository;
	private static final Logger logger = LogManager.getLogger("FirestationService");
	
	/**
     * Retrieves all firestations from the repository.
     * 
     * @return a list of all firestations
     */
	public List<Firestation> getFirestation() {
	    logger.info("Fetching all firestations from the repository");

	    // Retrieve all firestations
	    List<Firestation> firestations = firestationRepository.getFirestation();

	    logger.debug("Found {} firestations in the repository", firestations.size());
	    return firestations;
	}
	
	/**
     * Saves a new firestation to the repository.
     * If the firestation already exists, the save operation is aborted.
     * 
     * @param firestation the firestation to save
     */
	public void saveFirestation(Firestation firestation) {
	    logger.info("Attempting to save firestation at address: {}", firestation.getAddress());

	    // Check if the firestation already exists
	    if (!firestationRepository.getFirestation().contains(firestation)) {
	        logger.debug("Firestation does not exist, saving firestation at address: {}", firestation.getAddress());

	        // Save the firestation
	        firestationRepository.saveFirestation(firestation);
	        logger.info("Firestation saved successfully at address: {}", firestation.getAddress());
	    } else {
	        logger.error("Firestation already exists at address: {}. Save aborted.", firestation.getAddress());
	    }
	}
	
	/**
     * Updates an existing firestation in the repository based on its address.
     * If the firestation does not exist, the update operation is aborted.
     * 
     * @param firestation the firestation to update
     */
	public void updateFirestation(Firestation firestation) {
	    logger.info("Updating firestation at address: {}", firestation.getAddress());

	    // Retrieve the list of firestations once
	    List<Firestation> firestations = firestationRepository.getFirestation();

	    // Find the index of the firestation to update using IntStream.range()
	    OptionalInt indexOpt = IntStream.range(0, firestations.size())
	        .filter(i -> firestations.get(i).getAddress().equals(firestation.getAddress()))
	        .findFirst();

	    if (indexOpt.isPresent()) {
	        int index = indexOpt.getAsInt(); // Get the index value
	        logger.debug("Firestation found at index {} for address {}", index, firestation.getAddress());

	        logger.debug("Calling updateFirestation method in FirestationRepository");
	        firestationRepository.updateFirestation(index, firestation);
	        
	        logger.info("Firestation updated successfully at address {}", firestation.getAddress());
	    } else {
	        logger.error("Firestation at address {} does not exist. Update aborted.", firestation.getAddress());
	    }
	}
	
	/**
     * Deletes an existing firestation from the repository.
     * If the firestation does not exist, the delete operation is aborted.
     * 
     * @param firestation the firestation to delete
     */
	public void deleteFirestation(Firestation firestation) {
	    logger.info("Attempting to delete firestation at address: {}", firestation.getAddress());

	    // Check if the firestation exists
	    if (firestationRepository.getFirestation().contains(firestation)) {
	        logger.debug("Firestation found, deleting firestation at address: {}", firestation.getAddress());

	        // Delete the firestation
	        firestationRepository.deleteFirestation(firestation);
	        logger.info("Firestation deleted successfully at address: {}", firestation.getAddress());
	    } else {
	        logger.warn("Firestation not found at address: {}. Delete aborted.", firestation.getAddress());
	    }
	}
}
