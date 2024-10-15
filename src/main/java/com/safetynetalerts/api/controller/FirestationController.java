package com.safetynetalerts.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.model.DTO.FirestationDTO;
import com.safetynetalerts.api.service.AlertService;
import com.safetynetalerts.api.service.FirestationService;

/**
 * Controller for handling fire station-related API endpoints.
 * This controller provides methods for retrieving information about fire stations,
 * as well as creating, updating, and deleting fire stations.
 */
@RestController
@RequestMapping("/firestation")
public class FirestationController {
	
	@Autowired
	private AlertService alertService;
	@Autowired
	private FirestationService firestationService;
	private static final Logger logger = LogManager.getLogger("FirestationController");
	
	/**
     * Retrieves information about persons covered by a specific fire station number.
     * 
     * @param stationNumber the fire station number
     * @return a {@link FirestationDTO} containing details of persons under the fire station's coverage
     */
	@GetMapping
	public FirestationDTO getPersonPerStation(@RequestParam Integer stationNumber) {
	    logger.info("Received request to get person per station for station number: {}", stationNumber);
	    FirestationDTO result = alertService.getPersonPerStation(stationNumber);
	    logger.debug("Response for station number {}: {}", stationNumber, result);
	    return result;
	}

	/**
     * Creates a new fire station entry.
     * 
     * @param firestation the {@link Firestation} object containing the details of the new fire station to create
     */
	@PostMapping
	public void createFirestation(@RequestBody Firestation firestation) {
	    logger.info("Received request to create firestation: {}", firestation);
	    firestationService.saveFirestation(firestation);
	    logger.debug("Firestation created: {}", firestation);
	}

	/**
     * Updates an existing fire station entry.
     * 
     * @param firestation the {@link Firestation} object containing the updated details of the fire station
     */
	@PutMapping
	public void updateFirestation(@RequestBody Firestation firestation) {
	    logger.info("Received request to update firestation: {}", firestation);
	    firestationService.updateFirestation(firestation);
	    logger.debug("Firestation updated: {}", firestation);
	}

	/**
     * Deletes an existing fire station entry.
     * 
     * @param firestation the {@link Firestation} object representing the fire station to delete
     */
	@DeleteMapping
	public void deleteFirestation(@RequestBody Firestation firestation) {
	    logger.info("Received request to delete firestation: {}", firestation);
	    firestationService.deleteFirestation(firestation);
	    logger.debug("Firestation deleted: {}", firestation);
	}
}
