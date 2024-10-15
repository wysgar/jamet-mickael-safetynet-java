package com.safetynetalerts.api.service;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.PersonRepository;

/**
 * Service responsible for handling operations related to persons.
 * Provides methods to retrieve, save, update, and delete person records.
 */
@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	private static final Logger logger = LogManager.getLogger("PersonService");

	/**
     * Retrieves all persons from the repository.
     * 
     * @return a list of all persons
     */
	public List<Person> getPerson() {
	    logger.info("Fetching all persons from the repository");

	    // Retrieve all persons
	    List<Person> persons = personRepository.getPerson();

	    logger.debug("Found {} persons in the repository", persons.size());
	    return persons;
	}

	/**
     * Saves a new person to the repository.
     * If the person already exists, the save operation is aborted.
     * 
     * @param person the person to save
     */
	public void savePerson(Person person) {
	    logger.info("Attempting to save person: {} {}", person.getFirstName(), person.getLastName());

	    // Check if the person already exists in the repository
	    if (!personRepository.getPerson().contains(person)) {
	        logger.debug("Person does not exist, saving person: {} {}", person.getFirstName(), person.getLastName());

	        // Save the person
	        personRepository.savePerson(person);
	        logger.info("Person saved successfully: {} {}", person.getFirstName(), person.getLastName());
	    } else {
	        logger.error("Person already exists: {} {}. Save aborted.", person.getFirstName(), person.getLastName());
	    }
	}

	/**
     * Updates an existing person in the repository based on the person's first and last names.
     * If the person does not exist, the update operation is aborted.
     * 
     * @param person the person to update
     */
	public void updatePerson(Person person) {
	    logger.info("Updating person: {} {}", person.getFirstName(), person.getLastName());

	    // Retrieve the list of persons once
	    List<Person> persons = personRepository.getPerson();

	    // Find the index of the person to update using IntStream.range()
	    OptionalInt indexOpt = IntStream.range(0, persons.size())
	        .filter(i -> persons.get(i).getFirstName().equals(person.getFirstName())
	                  && persons.get(i).getLastName().equals(person.getLastName()))
	        .findFirst();

	    if (indexOpt.isPresent()) {
	        int index = indexOpt.getAsInt();
	        logger.debug("Person found at index {} for {} {}", index, person.getFirstName(), person.getLastName());

	        // Update the person
	        personRepository.updatePerson(index, person);
	        logger.info("Person updated successfully: {} {}", person.getFirstName(), person.getLastName());
	    } else {
	        logger.error("Person {} {} does not exist. Update aborted.", person.getFirstName(), person.getLastName());
	    }
	}

	/**
     * Deletes an existing person from the repository.
     * If the person does not exist, the delete operation is aborted.
     * 
     * @param person the person to delete
     */
	public void deletePerson(Person person) {
	    logger.info("Attempting to delete person: {} {}", person.getFirstName(), person.getLastName());

	    // Check if the person exists in the repository
	    if (personRepository.getPerson().contains(person)) {
	        logger.debug("Person found, deleting person: {} {}", person.getFirstName(), person.getLastName());

	        // Delete the person
	        personRepository.deletePerson(person);
	        logger.info("Person deleted successfully: {} {}", person.getFirstName(), person.getLastName());
	    } else {
	        logger.error("Person not found: {} {}. Delete aborted.", person.getFirstName(), person.getLastName());
	    }
	}
}
