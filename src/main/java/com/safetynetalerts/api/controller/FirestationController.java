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


@RestController
@RequestMapping("/firestation")
public class FirestationController {
	
	@Autowired
	private AlertService alertService;
	@Autowired
	private FirestationService firestationService;
	private static final Logger logger = LogManager.getLogger("FirestationController");
	
	@GetMapping
	private FirestationDTO getPersonPerStation(@RequestParam Integer stationNumber) {
	    logger.info("Received request to get person per station for station number: {}", stationNumber);
	    FirestationDTO result = alertService.getPersonPerStation(stationNumber);
	    logger.debug("Response for station number {}: {}", stationNumber, result);
	    return result;
	}

	@PostMapping
	public void createFirestation(@RequestBody Firestation firestation) {
	    logger.info("Received request to create firestation: {}", firestation);
	    firestationService.saveFirestation(firestation);
	    logger.debug("Firestation created: {}", firestation);
	}

	@PutMapping
	public void updateFirestation(@RequestBody Firestation firestation) {
	    logger.info("Received request to update firestation: {}", firestation);
	    firestationService.updateFirestation(firestation);
	    logger.debug("Firestation updated: {}", firestation);
	}

	@DeleteMapping
	public void deleteFirestation(@RequestBody Firestation firestation) {
	    logger.info("Received request to delete firestation: {}", firestation);
	    firestationService.deleteFirestation(firestation);
	    logger.debug("Firestation deleted: {}", firestation);
	}
}
