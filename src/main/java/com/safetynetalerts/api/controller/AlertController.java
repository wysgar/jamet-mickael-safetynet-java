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

/**
 * Controller for handling alert-related API endpoints.
 * This controller provides methods to retrieve information for child,
 * phone, fire, flood, person information and community emails.
 */
@RestController
public class AlertController {
	
	@Autowired
	private AlertService alertService;
	private static final Logger logger = LogManager.getLogger("AlertController");
	
	/**
     * Retrieves child for a given address.
     * This method returns a list of children (under 18 years old) living at the specified address.
     * 
     * @param address the address for which to retrieve child alert information
     * @return a {@link ChildAlertDTO} containing children and family members information
     */
	@GetMapping("/childAlert")
	public ChildAlertDTO getChildPerAddress(@RequestParam String address) {
	    logger.info("Received request for child alert for address: {}", address);
	    ChildAlertDTO result = alertService.getChildPerAddress(address);
	    logger.debug("Child alert response for address {}: {}", address, result);
	    return result;
	}

	/**
     * Retrieves phone numbers of persons covered by a specific fire station.
     * 
     * @param firestation the fire station number
     * @return a {@link PhoneAlertDTO} containing the phone numbers of persons in the fire station's coverage area
     */
	@GetMapping("/phoneAlert")
	public PhoneAlertDTO getPhonePerStation(@RequestParam Integer firestation) {
	    logger.info("Received request for phone alert for firestation: {}", firestation);
	    PhoneAlertDTO result = alertService.getPhonePerStation(firestation);
	    logger.debug("Phone alert response for firestation {}: {}", firestation, result);
	    return result;
	}

	/**
     * Retrieves information for persons and the fire station responsible for a specific address.
     * This includes medical information and emergency details.
     * 
     * @param address the address for which to retrieve fire information
     * @return a {@link FireDTO} containing details of persons and the associated fire station
     */
	@GetMapping("/fire")
	public FireDTO getPersonPerAddress(@RequestParam String address) {
	    logger.info("Received request for fire information for address: {}", address);
	    FireDTO result = alertService.getPersonPerAddress(address);
	    logger.debug("Fire information response for address {}: {}", address, result);
	    return result;
	}

	/**
     * Retrieves a list of homes in areas covered by the given fire stations and their respective inhabitants.
     * This is used during flood scenarios.
     * 
     * @param stations an array of fire station numbers
     * @return a list of {@link FloodDTO} objects containing home and person details for the specified stations
     */
	@GetMapping("/flood/stations")
	public List<FloodDTO> getHomePerStation(@RequestParam Integer[] stations) {
	    logger.info("Received request for flood information for stations: {}", Arrays.toString(stations));
	    List<FloodDTO> result = alertService.getHomePerStation(stations);
	    logger.debug("Flood information response for stations {}: {}", Arrays.toString(stations), result);
	    return result;
	}

	/**
     * Retrieves information for persons based on their last name.
     * This includes medical records and other details.
     * 
     * @param lastName the last name of the person(s) to search for
     * @return a list of {@link PersonInfoLastNameDTO} containing information of persons with the given last name
     */
	@GetMapping("/personInfolastName")
	public List<PersonInfoLastNameDTO> getInfoPerPerson(@RequestParam String lastName) {
	    logger.info("Received request for information for last name: {}", lastName);
	    List<PersonInfoLastNameDTO> result = alertService.getInfoPerPerson(lastName);
	    logger.debug("Information response for last name {}: {}", lastName, result);
	    return result;
	}

	/**
     * Retrieves a list of community email addresses based on the city.
     * 
     * @param city the city for which to retrieve email addresses
     * @return a list of emails of persons living in the specified city
     */
	@GetMapping("/communityEmail")
	public List<String> getEmail(@RequestParam String city) {
	    logger.info("Received request for community email for city: {}", city);
	    List<String> result = alertService.getEmail(city);
	    logger.debug("Community email response for city {}: {}", city, result);
	    return result;
	}
}
