package com.safetynetalerts.api.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.api.model.DTO.ChildAlertDTO;
import com.safetynetalerts.api.model.DTO.FireDTO;
import com.safetynetalerts.api.model.DTO.FloodDTO;
import com.safetynetalerts.api.model.DTO.PersonInfoLastNameDTO;
import com.safetynetalerts.api.model.DTO.PhoneAlertDTO;
import com.safetynetalerts.api.service.AlertService;

@RestController
public class AlertController {
	
	@Autowired
	private AlertService alertService;
	private static final Logger logger = LogManager.getLogger("AlertController");
	
	@GetMapping("/childAlert")
	private ChildAlertDTO getChildPerAddress(@RequestParam String address) {
	    logger.info("Received request for child alert for address: {}", address);
	    ChildAlertDTO result = alertService.getChildPerAddress(address);
	    logger.debug("Child alert response for address {}: {}", address, result);
	    return result;
	}

	@GetMapping("/phoneAlert")
	private PhoneAlertDTO getPhonePerStation(@RequestParam Integer firestation) {
	    logger.info("Received request for phone alert for firestation: {}", firestation);
	    PhoneAlertDTO result = alertService.getPhonePerStation(firestation);
	    logger.debug("Phone alert response for firestation {}: {}", firestation, result);
	    return result;
	}

	@GetMapping("/fire")
	private FireDTO getPersonPerAddress(@RequestParam String address) {
	    logger.info("Received request for fire information for address: {}", address);
	    FireDTO result = alertService.getPersonPerAddress(address);
	    logger.debug("Fire information response for address {}: {}", address, result);
	    return result;
	}

	@GetMapping("/flood/stations")
	private List<FloodDTO> getHomePerStation(@RequestParam Integer[] stations) {
	    logger.info("Received request for flood information for stations: {}", Arrays.toString(stations));
	    List<FloodDTO> result = alertService.getHomePerStation(stations);
	    logger.debug("Flood information response for stations {}: {}", Arrays.toString(stations), result);
	    return result;
	}

	@GetMapping("/personInfolastName")
	private List<PersonInfoLastNameDTO> getInfoPerPerson(@RequestParam String lastName) {
	    logger.info("Received request for information for last name: {}", lastName);
	    List<PersonInfoLastNameDTO> result = alertService.getInfoPerPerson(lastName);
	    logger.debug("Information response for last name {}: {}", lastName, result);
	    return result;
	}

	@GetMapping("/communityEmail")
	private List<String> getEmail(@RequestParam String city) {
	    logger.info("Received request for community email for city: {}", city);
	    List<String> result = alertService.getEmail(city);
	    logger.debug("Community email response for city {}: {}", city, result);
	    return result;
	}
}
