package com.safetynetalerts.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.service.PersonService;

/**
 * Controller for handling persons-related API endpoints.
 * This controller provides endpoints to save, update, and delete person records.
 */
@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	private static final Logger logger = LogManager.getLogger("PersonController");
	
	/**
     * Creates a new person.
     * 
     * @param person the {@link Person} object containing the details of the person to save
     */
	@PostMapping
	public void createPerson(@RequestBody Person person) {
	    logger.info("Received request to save person: {}", person);
	    personService.savePerson(person);
	    logger.debug("Person saved: {}", person);
	}

	/**
     * Updates an existing person.
     * 
     * @param person the {@link Person} object containing the updated details of the person
     */
	@PutMapping
	public void updatePerson(@RequestBody Person person) {
	    logger.info("Received request to update person: {}", person);
	    personService.updatePerson(person);
	    logger.debug("Person updated: {}", person);
	}

	/**
     * Deletes an existing person.
     * 
     * @param person the {@link Person} object representing the person to delete
     */
	@DeleteMapping
	public void deletePerson(@RequestBody Person person) {
	    logger.info("Received request to delete person: {}", person);
	    personService.deletePerson(person);
	    logger.debug("Person deleted: {}", person);
	}
}
